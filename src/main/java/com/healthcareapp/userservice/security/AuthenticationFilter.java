package com.healthcareapp.userservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcareapp.userservice.dto.UserDto;
import com.healthcareapp.userservice.exception.GenericException;
import com.healthcareapp.userservice.model.req.LoginRequestModel;
import com.healthcareapp.userservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserService userService;
	private Environment env;
	
	public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.userService = userService;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException exp) {
			throw new RuntimeException(exp);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String userName = ((User) auth.getPrincipal()).getUsername();
		UserDto userDto = null;
		try {
			userDto = userService.getUserById(userName);
		} catch (GenericException e) {
			e.printStackTrace();
		}
		
		String token = Jwts.builder()
				.setSubject(userDto.getEmail())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration-time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
				.compact();
		
		res.addHeader("token", token);
		res.addHeader("userName", userDto.getEmail());
	}

}
