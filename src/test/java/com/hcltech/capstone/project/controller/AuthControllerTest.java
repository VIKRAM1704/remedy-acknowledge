package com.hcltech.capstone.project.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hcltech.capstone.project.controller.AuthController;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.dto.LoginRequestDto;
import com.hcltech.capstone.project.dto.RegistrationDto;
import com.hcltech.capstone.project.service.AuthService;
class AuthControllerTest {
    @InjectMocks
 
    private AuthController authController;
    @Mock
 
    private AuthService authService;
    @BeforeEach
 
    void setUp() {
 
        MockitoAnnotations.openMocks(this);
 
    }
    @Test
 
    void testNewRegistration() {
 
        RegistrationDto registrationDto = new RegistrationDto(null, null, null);
 
        registrationDto.setUsername("testuser");
 
        registrationDto.setPassword("password");
 
        registrationDto.setRole("USER");
        when(authService.registerUser(any(RegistrationDto.class))).thenReturn("Registration Successful");
        ResponseEntity<String> response = authController.newRegistration(registrationDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registration Successful", response.getBody());
 
    }
    @Test
 
    void testLogin() {
 
        LoginRequestDto loginRequestDto = new LoginRequestDto(null, null);
 
        loginRequestDto.setUserName("testuser");
 
        loginRequestDto.setPassword("password");
        LoginDto loginDto = new LoginDto();
 
        loginDto.setUsername("testuser");
 
        loginDto.setRole("USER");
 
        loginDto.setToken("dummy-token");
        when(authService.login(any(LoginRequestDto.class))).thenReturn(loginDto);
        ResponseEntity<LoginDto> response = authController.login(loginRequestDto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(loginDto.getUsername(), response.getBody().getUsername());
        assertEquals(loginDto.getRole(), response.getBody().getRole());
        assertEquals(loginDto.getToken(), response.getBody().getToken());
 
    }
 
}