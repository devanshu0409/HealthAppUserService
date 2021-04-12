package com.healthcareapp.userservice.model.req;

import lombok.Data;

@Data
public class LoginRequestModel {
	
	private String email;
	private String password;

}
