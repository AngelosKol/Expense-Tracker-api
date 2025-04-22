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
public class MalformedTokenExceptionMapper extends BaseExceptionMapper implements ExceptionMapper<MalformedTokenException> {
    private static final Logger logger = LoggerFactory.getLogger(MalformedTokenExceptionMapper.class);
    @Context
    UriInfo uriInfo;
    @Override
    public Response toResponse(MalformedTokenException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                uriInfo.getRequestUri().toString(),
                Response.Status.UNAUTHORIZED.getStatusCode()
        );
        logger.error("Failed to extract claims from token: {}", Response.Status.UNAUTHORIZED.getStatusCode());
        return createJsonResponse(errorResponse, Response.Status.UNAUTHORIZED);
    }
}
