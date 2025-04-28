package com.ang.rest.user;


import com.ang.rest.auth.AuthenticatedUserUtil;
import com.ang.rest.domain.entity.User;
import com.ang.rest.exception.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
    @Inject AuthenticatedUserUtil authenticatedUserUtil;
    @Inject UserRepository userRepository;

    public User findByEmail() {
        String userEmail = authenticatedUserUtil.getAuthenticatedUserEmail();
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("No user found."));
    }
}
