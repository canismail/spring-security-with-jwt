package com.can.spring.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringSecurityApplication.class);
	}

	public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
	}
	
}
