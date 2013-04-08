package com.googlecode.easyec.cache.rest;

import com.googlecode.easyec.cache.rest.jaxb.CacheObject;
import org.jboss.resteasy.annotations.ClientResponseType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author JunJie
 */
@Path("/")
public interface CacheResource {

    @PUT
    @Path("put")
    @Consumes({ MediaType.APPLICATION_XML })
    void put(CacheObject object);

    @GET
    @Path("get/{key}/{cacheName}")
    @Consumes({ MediaType.APPLICATION_XML })
    Response get(@PathParam("cacheName") String cacheName, @PathParam("key") String key);

    @DELETE
    @Path("remove")
    @Consumes({ MediaType.APPLICATION_XML })
    void remove(CacheObject object);

    @GET
    @Path("status/{cacheName}")
    @Consumes({ MediaType.TEXT_PLAIN })
    @ClientResponseType(entityType = String.class)
    Response status(@PathParam("cacheName") String cacheName);
}
