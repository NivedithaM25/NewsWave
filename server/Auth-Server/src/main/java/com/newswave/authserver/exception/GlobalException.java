package com.newswave.authserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.newswave.authserver.response.AuthResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserAlreadyExists.class)
	//@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<AuthResponse> handleuserAlreadyExistsException(UserAlreadyExists msg) {
		
		return new ResponseEntity(AuthResponse.builder().message(msg.getMessage()),HttpStatus.CONFLICT);	
	}
	
	
	@ExceptionHandler(NullException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleNullExceptionException(UserAlreadyExists msg) {
		
		return new ResponseEntity(msg.getMessage(),HttpStatus.BAD_REQUEST);	
	}
}
