package com.ang.rest.exception;

import com.ang.rest.domain.dto.ErrorResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ResourceConflictExceptionMapper extends BaseExceptionMapper implements ExceptionMapper<ResourceConflictException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ResourceConflictException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                uriInfo.getRequestUri().toString(),
                Response.Status.CONFLICT.getStatusCode()
        );
        return createJsonResponse(error, Response.Status.CONFLICT);
    }
}
