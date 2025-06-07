package com.ang.rest.token;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.Token;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TokenRepository extends BaseRepository<Token, Integer> {


    Optional<Token> findByToken(String token) {
        return find("token", token).firstResultOptional();
    }
}
