package com.newswave.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.newswave.user.exception.UserAlreayExistsException;
import com.newswave.user.exception.UserNameShouldNotBeNull;
import com.newswave.user.model.UserModel;
import com.newswave.user.response.UserResponse;
import com.newswave.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	
	@Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getUserLogin_ValidUserCredentials() {
        // Mock data
        UserModel user = new UserModel("testUser","sample@gmail.com", "password");

        // Mock behavior
        when(userService.getUserByUserName("testUser")).thenReturn(null);
        when(userService.getUserRegistration(user))
                .thenReturn(new ResponseEntity<>(new UserResponse("testUser", null, "Registration successful"), HttpStatus.CREATED));

        // Test method
        ResponseEntity<?> responseEntity = userController.getUserLogin(user);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Registration successful", ((UserResponse) responseEntity.getBody()).getMessage());
    }

    @Test
    void getUserLogin_NullUserNameOrPassword() {
        // Mock data
        UserModel user = new UserModel(null, "Sample@gmail.com","password");

        // Test and assertions for the exception
        assertThrows(UserNameShouldNotBeNull.class, () -> userController.getUserLogin(user));
    }

    @Test
    void getUserLogin_ExistingUser() {
        // Mock data
        UserModel user = new UserModel("existingUser", "sample@gmail.com","password");

        // Mock behavior
        when(userService.getUserByUserName("existingUser")).thenReturn(user);

        // Test and assertions for the exception
        assertThrows(UserAlreayExistsException.class, () -> userController.getUserLogin(user));
    }
}
