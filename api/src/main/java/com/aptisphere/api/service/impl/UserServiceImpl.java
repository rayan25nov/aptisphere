package com.aptisphere.api.service.impl;

import com.aptisphere.api.dto.request.UpdateUserRoleRequest;
import com.aptisphere.api.dto.response.ApiResponse;
import com.aptisphere.api.entity.Role;
import com.aptisphere.api.entity.User;
import com.aptisphere.api.exception.ResourceNotFoundException;
import com.aptisphere.api.repository.RoleRepository;
import com.aptisphere.api.repository.UserRepository;
import com.aptisphere.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public ApiResponse updateUserRoles(Long id, UpdateUserRoleRequest request) {
        User user = getUserById(id);
        Set<Role> roles = new HashSet<>();

        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new ApiResponse(true, "User roles updated successfully");
    }
}