package com.healthcareapp.userservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5375339553219448236L;
	
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String encryptedPassword;

}
