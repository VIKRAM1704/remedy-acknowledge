package com.hcltech.capstone.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.dto.LoginRequestDto;
import com.hcltech.capstone.project.dto.RegistrationDto;
import com.hcltech.capstone.project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
@RestController
@Tag(name = "Auth Management", description = "Endpoints for Login and Register of user and Admin")
public class AuthController {
	  private final AuthService authService;

	    @Autowired
	    public AuthController(AuthService authService) {
	        this.authService = authService;
	    }

	    @PostMapping("/register")
	    @Operation(summary = "Register User/Admin", description = "Register the user or admin")
	    public ResponseEntity<String> newRegistration(@RequestBody  RegistrationDto registrationDto) {
	    	System.out.println(registrationDto);
	    	
	        return ResponseEntity.ok(authService.registerUser(registrationDto));
	    }

	    @PostMapping("/login")
	    @Operation(summary = "Login User/Admin", description = "Login the user or admin")
	    public ResponseEntity<LoginDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
	        return ResponseEntity.ok(authService.login(loginRequestDto));
	    }
}
