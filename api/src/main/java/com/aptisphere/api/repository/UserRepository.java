package com.aptisphere.api.repository;

import com.aptisphere.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used by Spring Security to load the user during login
    Optional<User> findByEmail(String email);

    // Used during registration to check if the email is already taken
    boolean existsByEmail(String email);
}