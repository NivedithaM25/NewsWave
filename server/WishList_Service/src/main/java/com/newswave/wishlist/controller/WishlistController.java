package com.newswave.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.newswave.wishlist.model.WishList;
import com.newswave.wishlist.response.AuthResponse;
import com.newswave.wishlist.response.WishListResponse;
import com.newswave.wishlist.service.WishListService;

import io.swagger.v3.oas.annotations.headers.Header;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

	@Autowired
	private WishListService wishListService;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/getAllFavorites/{userName}")
	public ResponseEntity<?> getAllFavouriteNews(
	        @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
	        @PathVariable String userName) {

		
		String authResponse=authorize(authorizationHeader, userName);
		
		if(authResponse.equals(userName)) {
			List<WishList> wishListResponse = wishListService.getAllFavoriteNews(userName);
			if (wishListResponse.isEmpty()) {
                return new ResponseEntity<>(AuthResponse.builder().message("No articles added to favourites yet!").build(), HttpStatus.OK);
            }
			return new ResponseEntity<>(wishListResponse,HttpStatus.OK);
		}
		return new ResponseEntity(AuthResponse.builder().message(authResponse).build(), HttpStatus.UNAUTHORIZED);
	}



	@PostMapping("/addNewsToFavs/{user}")
	public ResponseEntity<?> addNewsToWishList(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@RequestBody WishList wishList, @PathVariable("user") String user) {
		
		String authResponse=authorize(authorizationHeader, user);
		if(authResponse.equals(user)) {
			return new ResponseEntity(wishListService.addNewsToWishList(wishList, user), HttpStatus.OK);
		}
		
		return new ResponseEntity(AuthResponse.builder().message(authResponse).build(), HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("/deleteFavorites/{title}/{userName}")
	public ResponseEntity<?> deleteNewsArticle(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@PathVariable String title, @PathVariable String userName) {

		String authResponse=authorize(authorizationHeader, userName);
		
		if(authResponse.equals(userName)) {
			return new ResponseEntity(wishListService.deleteWishListByTitle(userName, title), HttpStatus.OK);
		}
		
		return new ResponseEntity(AuthResponse.builder().message(authResponse).build(), HttpStatus.UNAUTHORIZED);
		
	}
	
	@DeleteMapping("/deleteFavorites/{id}/{category}/{userName}")
	public ResponseEntity<?> deleteNewsArticle(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
			@PathVariable int id,@PathVariable String category, @PathVariable String userName) {

		String authResponse=authorize(authorizationHeader, userName);
		
		if(authResponse.equals(userName)) {
			return new ResponseEntity(wishListService.deleteWishlistItem(userName, id,category), HttpStatus.OK);
		}
		
		return new ResponseEntity(AuthResponse.builder().message(authResponse).build(), HttpStatus.UNAUTHORIZED);
		
	}
	
	private String authorize(String authorizationHeader,String userName) {
		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader); // Set your headers here
        //log.info(authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);
		try {
//			ResponseEntity<AuthResponse> response = restTemplate.exchange("http://localhost:5000/auth/validate/"+userName, HttpMethod.GET,
//					entity,AuthResponse.class);
			ResponseEntity<AuthResponse> response = restTemplate.exchange("http://newswave-auth-server.ca-central-1.elasticbeanstalk.com/auth/validate/"+userName, HttpMethod.GET,
					entity,AuthResponse.class);
			if(response.getStatusCode().is2xxSuccessful()) {
				return response.getBody().getMessage();
			}
		} catch (HttpClientErrorException.Unauthorized e) {
	        return "Unauthorized!";
	    }
		
		
		return "Unauthorized!";
	}
}
