package com.can.spring.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.can.spring.jwt.auth.JwtTokenProvider;
import com.can.spring.jwt.dto.UserRequestDto;

@RestController
@RequestMapping("/auth")
public class AuthController { 

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login") 
	public ResponseEntity<String> login(@RequestBody UserRequestDto request) {

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				request.getUserName(), request.getPassword());

		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);

		return new ResponseEntity<String>(jwtTokenProvider.createToken(auth),
				HttpStatus.CREATED);

	}

}
