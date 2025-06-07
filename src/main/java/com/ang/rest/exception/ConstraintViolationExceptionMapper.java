package com.ang.rest.exception;

import com.ang.rest.domain.dto.ErrorResponse;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ConstraintViolationExceptionMapper extends BaseExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;
    private static final Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                uriInfo.getRequestUri().toString(),
                Response.Status.CONFLICT.getStatusCode()
        );
        return createJsonResponse(errorResponse, Response.Status.CONFLICT);
    }
}
