package com.ang.rest.auth;


import com.ang.rest.domain.entity.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;


@ApplicationScoped
public class JwtProvider {

    @ConfigProperty(name = "security.jwt.expiration")
    long jwtExpiration;

    @ConfigProperty(name = "security.jwt.refresh-token.expiration")
    long refreshExpiration;

    public String generateToken(User user) {
        return Jwt.issuer("expense-api")
                .subject(user.getEmail())
                .claim("user_id", user.getId())
                .groups(Set.of("user"))
                .expiresIn(Duration.ofMillis(jwtExpiration))
                .sign();
    }

    public String generateRefreshToken(User user) {
        return Jwt.issuer("expense-api")
                .subject(user.getEmail())
                .claim("user_id", user.getId())
                .groups(Set.of("user"))
                .expiresIn(Duration.ofMillis(refreshExpiration))
                .sign();
    }
}