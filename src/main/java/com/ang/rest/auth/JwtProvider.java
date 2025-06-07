package com.ang.rest.auth;


import com.ang.rest.domain.entity.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;


@ApplicationScoped
public class JwtProvider {

    @ConfigProperty(name = "smallrye-jwt.verify.issuer")
    String issuer ;
    private static final Set<String> DEFAULT_ROLES = Collections.singleton("user");

    @ConfigProperty(name = "security.jwt.expiration")
    private long jwtExpirationMillis;

    @ConfigProperty(name = "security.jwt.refresh-token.expiration")
    private long refreshExpirationMillis;

    public String generateToken(User user) {
        return buildToken(user, jwtExpirationMillis);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, refreshExpirationMillis);
    }

    private String buildToken(User user, long expirationMillis) {
        Instant now = Instant.now();
        long expiresAt = now.plusMillis(expirationMillis).toEpochMilli();
        return Jwt.issuer(issuer)
                .subject(user.getEmail())
                .claim("user_id", user.getId())
                .groups(DEFAULT_ROLES)
                .expiresAt(expiresAt)
                .sign();
    }
}