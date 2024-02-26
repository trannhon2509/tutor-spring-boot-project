package com.project.tutor.repository;

import com.project.tutor.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Integer> {
    public Role findByRoleName (String roleName);

    Optional<Role> findById(int id);
}
