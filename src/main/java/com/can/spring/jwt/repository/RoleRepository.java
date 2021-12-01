package com.can.spring.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.can.spring.jwt.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
