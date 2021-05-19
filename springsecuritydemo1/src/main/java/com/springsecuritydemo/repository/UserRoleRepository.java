package com.springsecuritydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springsecuritydemo.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

}
