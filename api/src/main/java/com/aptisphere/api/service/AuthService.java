package com.aptisphere.api.service;

import com.aptisphere.api.dto.request.LoginRequest;
import com.aptisphere.api.dto.request.RegisterRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.dto.response.JwtAuthenticationResponse;

public interface AuthService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    ApiResponse register(RegisterRequest registerRequest);
}