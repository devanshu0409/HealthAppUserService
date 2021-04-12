package com.healthcareapp.userservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
	
	@ExceptionHandler(GenericException.class )
	    public ResponseEntity<String> handleGenericException(GenericException ex) {
	        return new ResponseEntity<>(ex.getMessage(),ex.getStatus());
	    }

}
