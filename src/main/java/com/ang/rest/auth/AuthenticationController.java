package com.ang.rest.auth;

import com.ang.rest.domain.dto.AuthenticationRequest;
import com.ang.rest.domain.dto.AuthenticationResponse;
import com.ang.rest.domain.dto.RegisterRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@RequiredArgsConstructor
public class AuthenticationController {

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) {
        authenticationService.register(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/authenticate")
    public Response authenticate(@Valid AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.login(request);
        return Response.ok(authenticationResponse).build();
    }
}
