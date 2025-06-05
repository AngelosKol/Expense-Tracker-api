package com.ang.rest.token;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.Token;

import java.util.Optional;

public class TokenRepository extends BaseRepository<Token, Integer> {


    Optional<Token> findByToken(String token) {
        return find("token", token).firstResultOptional();
    }
}
