package com.ang.rest.auth;

import com.ang.rest.domain.entity.User;
import com.ang.rest.jwt.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserUtil {

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        // Convert UserPrincipal back to User entity
        User user = new User();
        user.setId(userPrincipal.getId());
        user.setFirstname(userPrincipal.getFirstname());
        user.setLastname(userPrincipal.getLastname());
        user.setEmail(userPrincipal.getEmail());
        user.setPassword(userPrincipal.getPassword());
        
        return user;
    }
}
