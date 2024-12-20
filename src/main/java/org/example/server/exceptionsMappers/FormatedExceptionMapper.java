package org.example.server.exceptionsMappers;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public abstract class FormatedExceptionMapper<T extends Exception> implements ExceptionMapper<T> {
    @Context
    private HttpHeaders headers;

    protected Response toFormatedResponse(T exception, Response.Status status){
        if (headers.getAcceptableMediaTypes().contains(MediaType.APPLICATION_JSON_TYPE)) {
            String jsonErrorMessage = "{\"error\":\"" + exception.getMessage() + "\"}";
            return Response.status(status).entity(jsonErrorMessage).build();
        }
        if (headers.getAcceptableMediaTypes().contains(MediaType.APPLICATION_XML_TYPE)) {
            String xmlErrorMessage = "<error>" + exception.getMessage() + "</error>";
            return Response.status(status).entity(xmlErrorMessage).build();
        }
        return Response.status(status).entity(exception.getMessage()).build();
    }
}
