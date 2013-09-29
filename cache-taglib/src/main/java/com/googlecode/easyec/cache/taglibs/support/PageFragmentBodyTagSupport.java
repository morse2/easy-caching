package com.googlecode.easyec.cache.taglibs.support;

import com.googlecode.easyec.cache.CacheService;
import com.googlecode.easyec.cache.CacheStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext;

/**
 * 支持JSP页面部分标签体内容缓存的标签类。
 *
 * @author JunJie
 */
public class PageFragmentBodyTagSupport extends BodyTagSupport {

    public static final  String DEFAULT_PAGE_FRAGMENT_CACHE_KEY = "pageFragmentCache";
    private static final long   serialVersionUID                = -8735530165089257572L;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String cacheName = DEFAULT_PAGE_FRAGMENT_CACHE_KEY;
    protected String cacheKey;
    protected int    timeToLive;
    protected int    timeToIdle;

    public PageFragmentBodyTagSupport() {
        init();
    }

    /**
     * 从Spring上下文中获取缓存服务实例的方法
     *
     * @return 缓存服务实例对象
     */
    protected CacheService getCacheService() {
        WebApplicationContext ctx = getRequiredWebApplicationContext(pageContext.getServletContext());

        try {
            return null != ctx ? ctx.getBean(CacheService.class) : null;
        } catch (BeansException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    protected boolean checkAttrValid() {
        CacheService cacheService = getCacheService();
        if (isBlank(cacheName) || isBlank(cacheKey) || cacheService == null) {
            return false;
        }

        CacheStatus status = cacheService.getCacheStatus(cacheName);
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
        if (!checkAttrValid()) return EVAL_BODY_BUFFERED;

        CacheService cacheService = getCacheService();
        Object v = cacheService.get(cacheName, cacheKey);
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
                try {
                    printOut(bodyContent.getString());
                } finally {
                    bodyContent.clearBody();
                }
            }
        } else {
            CacheService cacheService = getCacheService();
            Boolean b = (Boolean) pageContext.getAttribute("doInCache");

            if (b != null && b) {
                String s = (String) cacheService.get(cacheName, cacheKey);

                try {
                    printOut(s);
                } finally {
                    pageContext.removeAttribute("doInCache");
                }
            } else {
                if (bodyContent != null) {
                    String s = bodyContent.getString();
                    if (isNotBlank(s)) {
                        b = cacheService.put(cacheName, cacheKey, s);
                        logger.debug("PUT operation. Cache name: [" + cacheName
                            + "], cache key: [" + cacheKey + "], operate result: ["
                            + b + "].");

                        try {
                            printOut(s);
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

    private void printOut(String s) throws JspTagException {
        if (isNotBlank(s)) {
            try {
                bodyContent.getEnclosingWriter().print(s);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new JspTagException(e);
            }
        }
    }
}
