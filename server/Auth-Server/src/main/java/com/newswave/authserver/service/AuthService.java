package com.newswave.authserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.newswave.authserver.exception.UserAlreadyExists;
import com.newswave.authserver.model.UserDetails;
import com.newswave.authserver.model.UserModel;
import com.newswave.authserver.repository.AuthRepository;
import com.newswave.authserver.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

	@Autowired
	AuthRepository authRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	public String saveuser(UserDetails userDetails){
		
		//log.info("inside service class....");
		Optional<UserModel> isUserExists =findByUserName(userDetails.getUserName());
		//log.info("is user exists"+isUserExists.getUserName());
		
		if(isUserExists!=null) {
			log.info("going inside");
				throw new UserAlreadyExists("The User you are trying to register already exists!");
		}else {
			log.info("saving user");
			UserModel usertoSave=new UserModel();
			usertoSave.setUserName(userDetails.getUserName());
			usertoSave.setPassword(userDetails.getPassword());
			authRepository.save(usertoSave);
			return "User Registered in Auth Server Successfully!";
		}		
	}
	 
	
	public Optional<UserModel> findByUserName(String userName) {
		log.info("checking if user exists..");
		Optional<UserModel> user=authRepository.findByUserName(userName);
		if(user.isPresent()) {
			return user;
		}
		return null;
	}


//	public String generateToken(UserDetails userDetails) throws UsernameNotFoundException{
//		// TODO Auto-generated method stub
//		
//		Optional<UserModel> user=findByUserName(userDetails.getUserName());
//		log.info(user.get().getUserName()+user.get().getPassword());
//		if(user==null) {
//			throw new UsernameNotFoundException("User not found with username: " + userDetails.getUserName());
//		}
//		
//		return jwtUtils.generateToken(userDetails);
//	}
	public String generateToken(UserDetails userDetails) throws UsernameNotFoundException {
	    Optional<UserModel> user = findByUserName(userDetails.getUserName());

	    if (!user.isPresent()) {
	        throw new UsernameNotFoundException("User not found with username: " + userDetails.getUserName());
	    }

	    UserModel foundUser = user.get();
	    if (!userDetails.getPassword().equals(foundUser.getPassword())) {
	        throw new UsernameNotFoundException("Invalid password for user: " + userDetails.getUserName());
	    }

	    return jwtUtils.generateToken(userDetails);
	}
	
	public String validateToken(String token,String userName) {
		
		Optional<UserModel> userDetails=findByUserName(userName);
				//Boolean isTokenValid=false;
		if(userDetails!=null) {
			UserDetails user=new UserDetails(userDetails.get().getUserName(), userDetails.get().getPassword());
//			isTokenValid=jwtUtils.validateToken(token, user.getUserName());
//			if(isTokenValid) {
//				log.info("seems token valid...");
//				return userName;
//			}
			log.info("inside if"+jwtUtils.getUsernameFromToken(token.substring(7)));
			return jwtUtils.getUsernameFromToken(token.substring(7));
		}
		 log.info("Service:outside if...");
		 
		 return "No user Found!";
	}
	
}
