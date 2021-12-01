package com.can.spring.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.can.spring.jwt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT a FROM UserEntity a where a.userName = ?1 ")
	public UserEntity listByUserName(String userName);
	
}
