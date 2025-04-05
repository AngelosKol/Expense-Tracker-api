package com.ang.rest.domain.dto;

public record ErrorResponse(
        String message,
        String path,
        int statusCode,
        long timeStamp
) {
    public ErrorResponse(String message, String path, int statusCode) {
        this(message, path, statusCode, getTimeStamp());
    }
    private static long getTimeStamp() {
        return   System.currentTimeMillis();
    }
}




