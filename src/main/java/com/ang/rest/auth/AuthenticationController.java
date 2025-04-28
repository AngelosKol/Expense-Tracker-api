package com.ang.rest.auth;


import com.ang.rest.domain.dto.AuthenticationRequest;
import com.ang.rest.domain.dto.AuthenticationResponse;
import com.ang.rest.domain.dto.RegisterRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Path("/api/v2/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @POST
    @Path("/register")
    public RestResponse<AuthenticationResponse> register(RegisterRequest request) {
        return RestResponse.ok(service.register(request));
    }

    @POST
    @Path("/authenticate")
    public RestResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return RestResponse.ok(service.authenticate(request));
    }

    @POST
    @Path("/refresh-token")
    public void refreshToken(
          @Context HttpServletRequest request,
          @Context  HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
