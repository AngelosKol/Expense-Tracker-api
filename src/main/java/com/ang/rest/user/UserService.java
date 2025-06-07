package com.ang.rest.user;

import com.ang.rest.domain.entity.Token;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.ResourceNotFoundException;
import com.ang.rest.token.TokenRepository;
import com.ang.rest.token.TokenType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    TokenRepository tokenRepository;


    @Transactional
    public boolean registerNewUser(String email, String password) {
        User existingUser = userRepository.findByEmail(email).firstResult();
        if (existingUser != null) {
            return false;
        }
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        User newUser = User.builder()
                .email(email)
                .password(hashed)
                .build();
        userRepository.persist(newUser);
        return true;
    }


    public Optional<User> validateCredentials(String email, String password) {
        User existingUSer = userRepository.findByEmail(email).firstResult();
        if(existingUSer == null){
            return Optional.empty();
        }
        if(!BCrypt.checkpw(password, existingUSer.password)) {
            return Optional.empty();
        }
        return Optional.of(existingUSer);
    }

    @Transactional
    public void persistJwtToken(String jwtString, User user) {
        Token token = new Token();
        token.token     = jwtString;
        token.tokenType = TokenType.BEARER;
        token.revoked   = false;
        token.expired   = false;
        token.user      = user;
        tokenRepository.persist(token);
    }

    public User findById(Long id) {
        return userRepository.findByIdOptional(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
