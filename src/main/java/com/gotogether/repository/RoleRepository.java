package com.gotogether.repository;

import com.gotogether.system.enums.RoleType;
import com.gotogether.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRolename(RoleType rolename);
}
