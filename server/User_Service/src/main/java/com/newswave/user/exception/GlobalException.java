package com.newswave.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.newswave.user.response.ExceptionResponse;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(UserNameShouldNotBeNull.class)
	public ResponseEntity<?> handleNullUserOrPassword(UserNameShouldNotBeNull ex){
		ExceptionResponse error=new ExceptionResponse(ex.getMessage());
		return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreayExistsException.class)
	public ResponseEntity<?> handleUserAlreadyExists(UserAlreayExistsException ex){
		ExceptionResponse error=new ExceptionResponse(ex.getMessage());
		return new ResponseEntity(error,HttpStatus.NOT_ACCEPTABLE);
	}
}
