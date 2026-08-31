package com.aptisphere.api.service.impl;

import com.aptisphere.api.dto.request.LoginRequest;
import com.aptisphere.api.dto.request.RegisterRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.JwtAuthenticationResponse;
import com.aptisphere.api.entity.Role;
import com.aptisphere.api.entity.User;
import com.aptisphere.api.exception.BadRequestException;
import com.aptisphere.api.exception.ResourceNotFoundException;
import com.aptisphere.api.repository.RoleRepository;
import com.aptisphere.api.repository.UserRepository;
import com.aptisphere.api.security.JwtTokenProvider;
import com.aptisphere.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtAuthenticationResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return JwtAuthenticationResponse.builder()
                .accessToken(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public ApiResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered.");
        }

        Role candidateRole = roleRepository.findByName("ROLE_CANDIDATE")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_CANDIDATE"));

        Set<Role> roles = new HashSet<>();
        roles.add(candidateRole);

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully!");
    }
}