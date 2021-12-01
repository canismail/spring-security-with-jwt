package com.can.spring.jwt.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

import com.can.spring.jwt.entity.RoleEntity;

@Mapper
public interface RolesMapper {

	public RolesMapper mapper = Mappers.getMapper(RolesMapper.class);
	
	public List<GrantedAuthority> roleEntityToGrandAuth(List<RoleEntity> entity); 
	
}
