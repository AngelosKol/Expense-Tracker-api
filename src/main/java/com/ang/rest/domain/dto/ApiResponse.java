package com.ang.rest.domain.dto;


import lombok.Builder;

import java.net.URI;

@Builder
public class ApiResponse<T> {
    public final URI location;
    public final Integer status;
    public final T body;

    public ApiResponse(int status, T body, URI location) {
        this.status = status;
        this.body = body;
        this.location = location;
    }

    public ApiResponse(int status) {
        this(status, null, null);
    }
    public ApiResponse(URI location) {
        this(null, null, location);
    }

    public ApiResponse(int status, T body) {
        this(status, body, null);
    }

    public boolean hasBody() {
        return body != null;
    }
    public boolean hasLocation() {
        return location != null;
    }

    public boolean hasStatus() {
        return status != null;
    }


}
