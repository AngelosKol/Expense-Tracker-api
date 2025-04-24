package com.ang.rest.auth;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthenticatedUserUtil {

    @Inject
    SecurityIdentity identity;

    public String getAuthenticatedUser() {
        return identity.getPrincipal().getName();
    }
}
