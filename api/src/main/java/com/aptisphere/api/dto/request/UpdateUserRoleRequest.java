package com.aptisphere.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRoleRequest {
    @NotEmpty(message = "At least one role must be provided")
    private Set<String> roles; // e.g., ["ROLE_ADMIN", "ROLE_CANDIDATE"]
}