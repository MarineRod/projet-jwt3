package com.diginamic.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.diginamic.demo.entity.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
   public UserInfo findByUsername(String username);
}