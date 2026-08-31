package com.aptisphere.api.service;

import com.aptisphere.api.dto.request.UpdateUserRoleRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    ApiResponse updateUserRoles(Long id, UpdateUserRoleRequest request);
}