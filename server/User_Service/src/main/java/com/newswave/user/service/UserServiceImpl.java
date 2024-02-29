package com.newswave.user.service;

import org.springframework.http.ResponseEntity;

import com.newswave.user.model.UserModel;
import com.newswave.user.response.UserResponse;

public interface UserServiceImpl {
	
	public ResponseEntity<UserResponse> getUserRegistration(UserModel user);
	public UserModel getUserByUserName(String userName);
}
