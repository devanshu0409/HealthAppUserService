package com.healthcareapp.userservice.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -133822503800850846L;
	
	private final HttpStatus status;
	private final String message;

}
