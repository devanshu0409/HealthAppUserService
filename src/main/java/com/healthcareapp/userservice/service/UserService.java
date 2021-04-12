package com.healthcareapp.userservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.healthcareapp.userservice.dto.UserDto;
import com.healthcareapp.userservice.exception.GenericException;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(final UserDto user) throws GenericException;
	
	UserDto updateUser(final UserDto updatedUser) throws GenericException;
	
	List<UserDto> getAllUsers() throws GenericException;
	
	UserDto getUserById(final String email) throws GenericException;
	
	void deleteUser(String email);
	
}
