package com.healthcareapp.userservice.model.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	
	@NotNull(message="E-mail can not be null")
	@Email
	private String email;
	
	@NotNull(message="First Name can not be null")
	private String firstName;
	
	@NotNull(message="Last Name can not be null")
	private String lastName;
	
	@NotNull(message="Password can not be null")
	@Size(min=8, message="Password should be of minimum lenght 8")
	private String password;

}
