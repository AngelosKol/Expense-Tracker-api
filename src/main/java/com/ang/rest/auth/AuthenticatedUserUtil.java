package com.ang.rest.auth;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class AuthenticatedUserUtil {

    @Inject
    SecurityIdentity identity;

    public String getAuthenticatedUserEmail() {
        return identity.getPrincipal().getName();
    }

    public Optional<Long> getUserIdIfClaimExists() {
        return identity.getAttribute("user_id");
    }
}