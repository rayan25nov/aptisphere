package com.aptisphere.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    @Builder.Default
    private String tokenType = "Bearer";

    // User details to hydrate the frontend state instantly
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}