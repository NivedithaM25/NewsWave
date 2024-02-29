package com.newswave.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.newswave.user.model.UserModel;
import com.newswave.user.model.UserReq;
import com.newswave.user.repository.UserRepository;
import com.newswave.user.response.AuthResponse;
import com.newswave.user.response.UserResponse;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	
	@Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserRegistration_SuccessfullyRegistersUser() {
        // Mock data
        UserModel user = new UserModel("testUser", "test@example.com", "password");
        AuthResponse authResponse = new AuthResponse("Registration successful");

        // Mock behaviors
        when(userRepository.save(user)).thenReturn(user);
        when(restTemplate.postForEntity(anyString(), any(UserReq.class), eq(AuthResponse.class)))
                .thenReturn(new ResponseEntity<>(authResponse, HttpStatus.CREATED));

        // Test method
        ResponseEntity<UserResponse> responseEntity = userService.getUserRegistration(user);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("testUser", responseEntity.getBody().getUserName());
        assertEquals("test@example.com", responseEntity.getBody().getEmail());
        assertEquals("Registration successful", responseEntity.getBody().getMessage());
    }

    @Test
    void getUserByUserName_ReturnsUserByUsername() {
        // Mock data
        UserModel user = new UserModel("testUser", "test@example.com", "password");

        // Mock behavior
        when(userRepository.getUserByUserName("testUser")).thenReturn(user);

        // Test method
        UserModel retrievedUser = userService.getUserByUserName("testUser");

        // Assertions
        assertEquals("testUser", retrievedUser.getUserName());
        assertEquals("test@example.com", retrievedUser.getEmail());
        assertEquals("password", retrievedUser.getPassword());
    }
}
