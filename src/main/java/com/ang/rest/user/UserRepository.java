package com.ang.rest.user;

import com.ang.rest.BaseRepository;
import com.ang.rest.domain.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;



@ApplicationScoped
public class UserRepository extends BaseRepository<User,Long> {
   public PanacheQuery<User> findByEmail(String email) {
      return find("lower(name) = ?1", "%" + email.toLowerCase() + "%");
   }



}
