package com.can.spring.jwt.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.can.spring.jwt.auth.JwtTokenProvider;
import com.can.spring.jwt.auth.service.MyUserDetailsService;

public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private MyUserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");

		if (StringUtils.hasLength(token) && token.startsWith("Bearer ")) {
			token = token.substring("Bearer ".length());
		}

		if (token != null) {
			String userName = jwtTokenProvider.getUsername(token);

			UserDetails user = null;

			if (userName != null) {
				user = userDetailService.loadUserByUsername(userName);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities());

				auth.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));

				// Security bilgilerini local storage gibi tutuyor. Local thread
				// yaratarak.
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		}

		filterChain.doFilter(request, response);

	}

}
