package com.ang.rest.domain.dto;


import lombok.Data;

@Data
public class ErrorResponse {
    public String message;
    public String path;
    public int statusCode;
    public long timeStamp;


    public ErrorResponse(String message,  String path ,int statusCode) {
        this.message = message;
        this.path = path;
        this.statusCode = statusCode;
        this.timeStamp = System.currentTimeMillis();
    }
}
