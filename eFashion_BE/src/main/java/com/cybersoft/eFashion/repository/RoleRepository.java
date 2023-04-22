package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {

    Roles findById(int id);

}
