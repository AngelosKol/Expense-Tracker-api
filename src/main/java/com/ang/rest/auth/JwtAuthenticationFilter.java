package com.ang.rest.auth;

import com.ang.rest.token.TokenRepository;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    JwtService jwtService;

    @Inject
    TokenRepository tokenRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        logger.info("Request path: {}", path);

        if (path.startsWith("api/v1/auth")) {
            logger.info("Skipping JWT filter for auth path.");
            return;
        }

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or invalid Authorization header");
            return;
        }

        String jwt = authHeader.substring(7);
        logger.info("JWT received: {}", jwt);

        try {
            String userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null) {
                boolean isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);

                if (jwtService.isTokenValid(jwt, userEmail) && isTokenValid) {
                    SecurityIdentityAugmentor.setSecurityIdentity(userEmail); // You’ll need to create this utility
                    logger.info("Token is valid. Authenticated user: {}", userEmail);
                }
            }
        } catch (Exception e) {
            logger.error("JWT processing error: {}", e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}