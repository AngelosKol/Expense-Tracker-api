package com.ang.rest.auth;


import com.ang.rest.domain.dto.AuthenticationRequest;
import com.ang.rest.domain.dto.AuthenticationResponse;
import com.ang.rest.domain.dto.RegisterRequest;
import com.ang.rest.domain.entity.Token;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.InvalidCredentialsException;
import com.ang.rest.exception.ResourceConflictException;
import com.ang.rest.token.TokenRepository;
import com.ang.rest.token.TokenType;
import com.ang.rest.user.UserRepository;
import com.ang.rest.user.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    UserService userService;
    @Inject
    JwtProvider jwtProvider;

    @Inject
    JsonWebToken jwt;

    @Transactional
    public void register(RegisterRequest registerRequest) {
      String email = registerRequest.getEmail().trim().toLowerCase();
      String password = registerRequest.getPassword();
      boolean created = userService.registerNewUser(email, password);
      if(!created) {
          throw new ResourceConflictException("Email already exists");
      }
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail().trim().toLowerCase();
        String password = authenticationRequest.getPassword();
        User found = userService.validateCredentials(email, password)
                .orElseThrow(() -> new InvalidCredentialsException("User does not exist or credentials are wrong"));
        String accessToken  = jwtProvider.generateToken(found);
        String refreshToken = jwtProvider.generateRefreshToken(found);
        // userService.persistJwtToken(accessToken, user);
        // userService.persistJwtToken(refreshToken, user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public User getAuthenticatedUser() {
        Long userId = jwt.getClaim("user_id");
        return userService.findById(userId);
    }

}
