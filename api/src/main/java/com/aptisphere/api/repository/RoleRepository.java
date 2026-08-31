package com.aptisphere.api.repository;

import com.aptisphere.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Used to find specific roles like "ROLE_ADMIN" or "ROLE_CANDIDATE"
    Optional<Role> findByName(String name);
}