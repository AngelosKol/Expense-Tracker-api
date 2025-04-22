package com.ang.rest.exception;

import com.ang.rest.domain.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;

public class BaseExceptionMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected Response createJsonResponse(ErrorResponse errorResponse, Response.Status status) {
        try {
            String json = objectMapper.writeValueAsString(errorResponse);
            return Response.status(status).entity(json).build();
        }catch (JsonProcessingException exception) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unhandled exception").build();
        }
    }
}