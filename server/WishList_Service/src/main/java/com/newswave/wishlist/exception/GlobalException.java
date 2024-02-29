package com.newswave.wishlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.newswave.wishlist.response.AuthResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(AlreadyExistinList.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ResponseEntity<?> handleAlreadyExistinList(AlreadyExistinList ex){
	    String errorMessage = ex.getMessage(); // Assuming getMessage() returns the error message
	    
	    AuthResponse errorResponse = AuthResponse.builder().message(errorMessage).build();
	    
	    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

}
