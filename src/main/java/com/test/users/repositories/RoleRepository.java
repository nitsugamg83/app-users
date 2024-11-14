package com.test.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.users.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
