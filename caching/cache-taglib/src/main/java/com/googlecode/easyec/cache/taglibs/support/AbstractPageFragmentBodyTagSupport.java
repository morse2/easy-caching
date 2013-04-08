package com.googlecode.easyec.cache.taglibs.support;

import com.googlecode.easyec.cache.CacheElement;
import com.googlecode.easyec.cache.CacheService;
import com.googlecode.easyec.cache.CacheStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-8-13
 * Time: 下午1:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPageFragmentBodyTagSupport extends BodyTagSupport {

    public static final String DEFAULT_PAGE_FRAGMENT_CACHE_KEY = "pageFragmentCache";
    private static final long serialVersionUID = -7123095538694909398L;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String cacheName = DEFAULT_PAGE_FRAGMENT_CACHE_KEY;
    protected String cacheKey;
    protected int timeToLive;
    protected int timeToIdle;

    protected AbstractPageFragmentBodyTagSupport() {
        init();
    }

    protected CacheService getCacheService() {
        return getRequiredWebApplicationContext(pageContext.getServletContext()).getBean(CacheService.class);
    }

    public String getCacheKey() {
        return cacheKey;
    }

    /**
     * 封装一个<code>CacheElement</code>对象的实例。
     *
     * @param content
     * @return
     */
    abstract protected CacheElement getCacheElementImpl(String content);

    protected boolean checkAttrValid() {
        if (isBlank(cacheName) || isBlank(cacheKey) || getCacheService() == null) {
            return false;
        }

        CacheStatus status = getCacheService().getCacheStatus(cacheName);
        switch (status) {
            case STATUS_ERROR:
            case STATUS_SHUTDOWN:
            case STATUS_UNINITIALISED:
            case STATUS_UNKNOWN:
                logger.warn("Cache service has some errors, please check it!");
                return false;
        }

        return true;
    }

    @Override
    public int doStartTag() throws JspException {
        if (!checkAttrValid()) {
            return EVAL_BODY_BUFFERED;
        }

        CacheService cacheService = getCacheService();

        Object o = cacheService.get(cacheName, getCacheKey());
        if (o == null) {
            return EVAL_BODY_BUFFERED;
        }

        if (!(o instanceof CacheElement)) {
            return EVAL_BODY_BUFFERED;
        }

        Object v = ((CacheElement) o).getValue();
        if (v == null || !(v instanceof String)) {
            return EVAL_BODY_BUFFERED;
        }

        pageContext.setAttribute("doInCache", true);

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        if (!checkAttrValid()) {
            if (bodyContent != null) {
                String s = bodyContent.getString();

                if (isNotBlank(s)) {
                    try {
                        bodyContent.getEnclosingWriter().print(s);
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);

                        throw new JspTagException(e);
                    }
                }
            }
        } else {
            CacheService cacheService = getCacheService();

            Boolean b = (Boolean) pageContext.getAttribute("doInCache");

            if (b != null && b) {
                CacheElement o = (CacheElement) cacheService.get(cacheName, getCacheKey());

                try {
                    pageContext.getOut().print(o.getValue());
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);

                    throw new JspTagException(e);
                } finally {
                    pageContext.removeAttribute("doInCache");
                }
            } else {
                if (bodyContent != null) {
                    String s = bodyContent.getString();

                    if (isNotBlank(s)) {

                        b = cacheService.put(cacheName, getCacheKey(), getCacheElementImpl(s));
                        logger.debug("PUT operation. Cache name: [" + cacheName
                                + "], cache key: [" + getCacheKey() + "], operate result: ["
                                + b + "].");

                        try {
                            bodyContent.getEnclosingWriter().print(s);
                        } catch (IOException e) {
                            logger.error(e.getMessage(), e);

                            throw new JspTagException(e);
                        } finally {
                            bodyContent.clearBody();
                        }
                    }
                }
            }
        }

        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    private void init() {
        cacheName = DEFAULT_PAGE_FRAGMENT_CACHE_KEY;
        cacheKey = null;
        timeToLive = timeToIdle = 0;
    }
}
