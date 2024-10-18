package com.diginamic.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.diginamic.demo.entity.UserRole;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
   public UserRole findByName(String name);
}
