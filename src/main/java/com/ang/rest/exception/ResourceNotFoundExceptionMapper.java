package com.ang.rest.exception;


import com.ang.rest.domain.dto.ErrorResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ResourceNotFoundExceptionMapper extends  BaseExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundExceptionMapper.class);
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ResourceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                uriInfo.getRequestUri().toString(),
                Response.Status.NOT_FOUND.getStatusCode());
        return createJsonResponse(errorResponse, Response.Status.NOT_FOUND);

    }
}
