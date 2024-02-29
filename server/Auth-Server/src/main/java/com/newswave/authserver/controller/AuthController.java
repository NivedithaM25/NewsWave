package com.newswave.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newswave.authserver.exception.NullException;
import com.newswave.authserver.model.UserDetails;
import com.newswave.authserver.response.AuthResponse;
import com.newswave.authserver.response.TokenResponse;
import com.newswave.authserver.service.AuthService;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	AuthService authService;
	
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> userRegistration(@RequestBody UserDetails userDetails){
		log.info("inside controller...");
		
		AuthResponse response=new AuthResponse();
		//
		try {
			
			if(userDetails.getUserName()==null || userDetails.getPassword().isEmpty() || userDetails.getUserName().isEmpty() || userDetails.getPassword().isEmpty()) {
				throw new NullException("userName or password should not be null!!");
			}
			String serviceResponse = authService.saveuser(userDetails);
			response.setMessage(serviceResponse);
			//response.setUserName(userDetails.getUserName());
			return new ResponseEntity(response,HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity(
					AuthResponse.builder().message(e.getMessage()).build()
					,HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/generatetoken")
	public ResponseEntity<TokenResponse> generatetoken(@RequestBody UserDetails userDetails) throws Exception{
		
		
		String token=authService.generateToken(userDetails);

		return new ResponseEntity(TokenResponse.builder().token(token).build(),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/validate/{userName}")
	public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String token,@PathVariable("userName") String userName){
		
		log.info(token);
		String response=authService.validateToken(token,userName);
		
		if(response.equals(userName)) {
			return new ResponseEntity(AuthResponse.builder().message(response).build(),HttpStatus.OK);
		}else {
			return new ResponseEntity(AuthResponse.builder().message("Token Not Valid!").build(),HttpStatus.UNAUTHORIZED);	
		}
		
	}
}
