package com.can.spring.jwt.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.can.spring.jwt.entity.UserEntity;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

	public Long id;
	private String userName;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String userName, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl getUserDetails(UserEntity user) {
		List<GrantedAuthority> authList = new ArrayList<>();
		
//		try {
//			// TODO
//			authList = RolesMapper.mapper.roleEntityToGrandAuth(user.getRoles());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		authList.add(new SimpleGrantedAuthority("user"));
		
		return new UserDetailsImpl(user.getId(),  user.getUserName(),
				user.getPassword(), authList);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
