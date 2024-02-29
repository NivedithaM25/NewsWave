package com.newswave.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newswave.user.exception.UserAlreayExistsException;
import com.newswave.user.exception.UserNameShouldNotBeNull;
import com.newswave.user.model.UserModel;
import com.newswave.user.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> getUserLogin(@RequestBody UserModel user){
		
		if(user.getUserName()==null || user.getPassword()==null) {
			throw new UserNameShouldNotBeNull("User Name and Password Should Not Be Null!");
		}

		if(userService.getUserByUserName(user.getUserName())!=null) {
			throw new UserAlreayExistsException("UserName Already Exists, Please select another UserName");
		}
		return userService.getUserRegistration(user);
	}

}
