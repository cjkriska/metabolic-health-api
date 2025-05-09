package com.charliekriska.metabolic_health_api.controller;

import com.charliekriska.metabolic_health_api.exception.BadRequestException;
import com.charliekriska.metabolic_health_api.exception.InvalidCredentialsException;
import com.charliekriska.metabolic_health_api.exception.ResourceNotFoundException;
import com.charliekriska.metabolic_health_api.model.AuthProvider;
import com.charliekriska.metabolic_health_api.model.User;
import com.charliekriska.metabolic_health_api.payload.ApiResponse;
import com.charliekriska.metabolic_health_api.payload.AuthResponse;
import com.charliekriska.metabolic_health_api.payload.LoginRequest;
import com.charliekriska.metabolic_health_api.payload.SignUpRequest;
import com.charliekriska.metabolic_health_api.repository.UserRepository;
import com.charliekriska.metabolic_health_api.security.TokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.createToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token));

        } catch(Exception e) {
            throw new InvalidCredentialsException("Incorrect username or password.");
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

}
