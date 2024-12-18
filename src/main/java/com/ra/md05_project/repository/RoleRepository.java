package com.ra.md05_project.repository;

import com.ra.md05_project.model.constant.RoleName;
import com.ra.md05_project.model.entity.ver1.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(RoleName roleName);
}
