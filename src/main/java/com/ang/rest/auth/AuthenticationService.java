package com.ang.rest.auth;


import com.ang.rest.auth.JwtProvider;
import com.ang.rest.domain.dto.AuthenticationRequest;
import com.ang.rest.domain.dto.AuthenticationResponse;
import com.ang.rest.domain.dto.RegisterRequest;
import com.ang.rest.domain.entity.Token;
import com.ang.rest.domain.entity.User;
import com.ang.rest.token.TokenRepository;
import com.ang.rest.token.TokenType;
import com.ang.rest.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.io.IOException;

@ApplicationScoped
public class AuthenticationService {
    @Inject
    UserRepository repository;
    @Inject
    TokenRepository tokenRepository;
    @Inject
    JwtProvider jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(BcryptUtil.bcryptHash(request.getPassword()))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (!BcryptUtil.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}


