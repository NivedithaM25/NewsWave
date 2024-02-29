package com.newswave.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.newswave.user.exception.UserAlreayExistsException;
//import com.newswave.user.kafka.KafkaProducer;
import com.newswave.user.model.UserModel;
import com.newswave.user.model.UserReq;
import com.newswave.user.repository.UserRepository;
import com.newswave.user.response.AuthResponse;
import com.newswave.user.response.UserResponse;

@Service
public class UserService implements UserServiceImpl{
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	RestTemplate restTemplate;
	
//	@Autowired
//	KafkaProducer kafkaProducer;

	@Override
	public ResponseEntity<UserResponse> getUserRegistration(UserModel user) {

		userRepository.save(user);
		UserReq userReq=new UserReq(user.getUserName(),user.getEmail(),user.getPassword());
		
		ResponseEntity<AuthResponse> response= restTemplate.postForEntity("https://jmj6xkdgjj.execute-api.ca-central-1.amazonaws.com/Auth/register",userReq, AuthResponse.class);
		//ResponseEntity<AuthResponse> response= restTemplate.postForEntity("http://localhost:5000/auth/register",userReq, AuthResponse.class);
		
		UserResponse savedUser=new UserResponse(user.getUserName(), user.getEmail(), response.getBody().getMessage());
		if(response.getStatusCode().is2xxSuccessful()) {
			//kafkaProducer.sendMessage("HttpCOde 200");
			
			return new ResponseEntity(savedUser,HttpStatus.CREATED);
		}
		return new ResponseEntity(savedUser,HttpStatus.BAD_REQUEST);
	}

	@Override
	public UserModel getUserByUserName(String userName) {
		return userRepository.getUserByUserName(userName);
	}
	

}
