package com.newswave.user.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	
	private String message;
    private String token;
    public AuthResponse(String message) {
		// TODO Auto-generated constructor stub
    	this.message=message;
	}
}