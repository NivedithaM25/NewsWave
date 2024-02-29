package com.newswave.authserver.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.newswave.authserver.model.UserDetails;
import com.newswave.authserver.model.UserModel;
import com.newswave.authserver.service.AuthService;
import com.newswave.authserver.utils.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	AuthService authService;
	@Autowired
	private JwtUtils jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
				// only the Token
				if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
					jwtToken = requestTokenHeader.substring(7);
					//jwtToken=requestTokenHeader.split(" ")[1].trim();
					//jwtToken = requestTokenHeader.substring("Bearer ".length());
					try {
						username = jwtTokenUtil.getUsernameFromToken(jwtToken);
					} catch (IllegalArgumentException e) {
						System.out.println("Unable to get JWT Token");
					} catch (ExpiredJwtException e) {
						System.out.println("JWT Token has expired");
					}
				} else {
					logger.warn("JWT Token does not begin with Bearer String");
				}
				
				// Once we get the token validate it.
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					Optional<UserModel> userDetails = this.authService.findByUserName(username);
					
					UserDetails user=new UserDetails(userDetails.get().getUserName(), userDetails.get().getPassword());
					// if token is valid configure Spring Security to manually set
					// authentication
					if (jwtTokenUtil.validateToken(jwtToken, user.getUserName())) {

						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, null);
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						// After setting the Authentication in the context, we specify
						// that the current user is authenticated. So it passes the
						// Spring Security Configurations successfully.
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
				filterChain.doFilter(request, response);
			}

		
	

}
