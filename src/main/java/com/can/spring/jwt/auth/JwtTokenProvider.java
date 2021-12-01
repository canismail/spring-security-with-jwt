package com.can.spring.jwt.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	private String secretKey = "changedit";

	private long validityInMilliseconds = 3600000; // 1h

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	private UserDetailsService userDetailsService;

	public String createToken(Authentication authentication) {

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		claims.put("auth",userDetails.getAuthorities().stream()
						.map(s -> new SimpleGrantedAuthority(s.getAuthority()))
						.filter(Objects::nonNull).collect(Collectors.toList()));

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return isTokenExpired(token);
		} catch (JwtException | IllegalArgumentException e) {
			throw new NullPointerException("Expired or invalid JWT token : "
					+ HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean isTokenExpired(String token) {
		Date exp = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
				.getBody().getExpiration();
		return exp.after(new Date());
	}

}
