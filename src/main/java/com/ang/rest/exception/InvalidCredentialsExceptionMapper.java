package com.ang.rest.exception;

import com.ang.rest.domain.dto.ErrorResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

public class InvalidCredentialsExceptionMapper extends BaseExceptionMapper<InvalidCredentialsException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(InvalidCredentialsException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                uriInfo.getRequestUri().toString(),
                Response.Status.CONFLICT.getStatusCode()
        );
        return createJsonResponse(errorResponse, Response.Status.CONFLICT);
    }
}
