package com.ang.rest.user;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.User;

import java.util.Optional;



public interface UserRepository extends BaseRepository<User,Long> {
   Optional<User> findByEmail(String email);



}
