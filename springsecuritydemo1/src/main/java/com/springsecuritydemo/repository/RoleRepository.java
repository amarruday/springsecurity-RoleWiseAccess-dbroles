package com.springsecuritydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecuritydemo.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	public Role findByRolename(String rolename);

}
