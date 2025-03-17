package com.charliekriska.metabolic_health_api.controller;

import com.charliekriska.metabolic_health_api.exception.ResourceNotFoundException;
import com.charliekriska.metabolic_health_api.model.User;
import com.charliekriska.metabolic_health_api.repository.UserRepository;
import com.charliekriska.metabolic_health_api.security.CurrentUser;
import com.charliekriska.metabolic_health_api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

}
