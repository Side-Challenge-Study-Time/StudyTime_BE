package com.challenge.studytime.domain.role.repositry;

import com.challenge.studytime.domain.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositry extends JpaRepository<Role,Long> {
}
