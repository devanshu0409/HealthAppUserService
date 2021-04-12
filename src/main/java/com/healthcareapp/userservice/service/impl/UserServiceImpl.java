package com.healthcareapp.userservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcareapp.userservice.dao.UserEntity;
import com.healthcareapp.userservice.dto.UserDto;
import com.healthcareapp.userservice.exception.GenericException;
import com.healthcareapp.userservice.repository.UserRepository;
import com.healthcareapp.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto user) throws GenericException {
		Optional<UserEntity> resultedUser = repository.findById(user.getEmail());
		if (!resultedUser.isPresent()) {
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			UserEntity entity = mapper.map(user, UserEntity.class);
			entity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
			UserEntity savedUser = repository.save(entity);

			return mapper.map(savedUser, UserDto.class);
		} else {
			throw new GenericException(HttpStatus.BAD_REQUEST, "Email Address Already Exists");
		}
	}

	@Override
	public UserDto updateUser(UserDto updatedUser) throws GenericException {
		Optional<UserEntity> resultedUser = repository.findById(updatedUser.getEmail());
		if (resultedUser.isPresent()) {
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			UserEntity entity = mapper.map(updatedUser, UserEntity.class);
			entity.setEncryptedPassword(passwordEncoder.encode(updatedUser.getPassword()));
			UserEntity savedUser = repository.save(entity);
			
			return mapper.map(savedUser, UserDto.class);
		} else {
			throw new GenericException(HttpStatus.NOT_FOUND, "User Does not Exists");
		}
	}

	@Override
	public List<UserDto> getAllUsers() throws GenericException {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<UserEntity> users = repository.findAll();
		List<UserDto> resultedUser = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(users)) {
			users.stream().forEach(user -> resultedUser.add(mapper.map(user, UserDto.class)));
			return resultedUser;
		}else {
			throw new GenericException(HttpStatus.NO_CONTENT, "No Users Found");
		}
		
		
	}

	@Override
	public UserDto getUserById(String email) throws GenericException {
		Optional<UserEntity> user = repository.findById(email);
		if (user.isPresent()) {
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			return mapper.map(user.get(), UserDto.class);
		} else {
			throw new GenericException(HttpStatus.NOT_FOUND, "User Does not Exists");
		}
	}

	@Override
	public void deleteUser(String email) {
		repository.deleteById(email);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = repository.findById(username);
		if (user.isPresent()) {
			return new User(user.get().getEmail(), user.get().getEncryptedPassword(), true, true, true, true,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

}
