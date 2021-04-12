package com.healthcareapp.userservice.model.res;

import lombok.Data;

@Data
public class CreateUserResponse {

	private String email;
	private String firstName;
	private String lastName;

}
