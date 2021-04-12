package com.healthcareapp.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcareapp.userservice.dto.UserDto;
import com.healthcareapp.userservice.exception.GenericException;
import com.healthcareapp.userservice.model.req.CreateUserRequest;
import com.healthcareapp.userservice.model.res.CreateUserResponse;
import com.healthcareapp.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/status")
	public String getStatus() {
		return "Working";
	}
	
	@GetMapping
	public ResponseEntity<List<CreateUserResponse>> getAllUser() throws GenericException {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<UserDto> createdUserDto = userService.getAllUsers();
		List<CreateUserResponse> response = new ArrayList<>();
		createdUserDto.stream().forEach(user -> response.add(mapper.map(user, CreateUserResponse.class)));

		if (!response.isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<CreateUserResponse> getUserById(@PathVariable String email) throws GenericException {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto createdUserDto = userService.getUserById(email);

		if (createdUserDto != null) {
			return new ResponseEntity<>(mapper.map(createdUserDto, CreateUserResponse.class), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest user) throws GenericException {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = mapper.map(user, UserDto.class);
		UserDto createdUserDto = userService.createUser(userDto);

		if (createdUserDto != null) {
			return new ResponseEntity<>(mapper.map(createdUserDto, CreateUserResponse.class), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<CreateUserResponse> updateUser(@Valid @RequestBody CreateUserRequest user) throws GenericException {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = mapper.map(user, UserDto.class);
		UserDto createdUserDto = userService.updateUser(userDto);

		if (createdUserDto != null) {
			return new ResponseEntity<>(mapper.map(createdUserDto, CreateUserResponse.class), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<CreateUserResponse> deleteUser(@PathVariable String email) {
		
		userService.deleteUser(email);
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
