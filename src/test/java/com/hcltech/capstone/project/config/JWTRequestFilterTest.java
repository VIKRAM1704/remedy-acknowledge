package com.hcltech.capstone.project.config;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcltech.capstone.project.dto.DecodeTokenDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.util.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

class JWTRequestFilterTest {

	@Mock

	private JWTUtils jwtUtils;

	@Mock

	private LoginRepository loginRepository;

	@InjectMocks

	private JWTRequestFilter jwtRequestFilter;

	private MockHttpServletRequest request;

	@BeforeEach

	void setUp() {

		MockitoAnnotations.openMocks(this);

		request = new MockHttpServletRequest();

	}

	@Test

	void testExtractJwtToken_ValidToken() {

		String token = jwtRequestFilter.extractJwtToken("Bearer validToken");

		assertEquals("validToken", token);

	}

	@Test

	void testExtractJwtToken_InvalidToken() {

		String token = jwtRequestFilter.extractJwtToken("InvalidToken");

		assertNull(token);

	}

	@Test

	void testGetUser_UserExists() {

		Login login = new Login();

		login.setUsername("testUser");

		when(loginRepository.findByUsername("testUser")).thenReturn(Optional.of(login));

		Login result = jwtRequestFilter.getUser("testUser");

		assertEquals("testUser", result.getUsername());

	}

	@Test
	void testShouldNotSkipFilter() throws Exception {
		request.addHeader("Authorization", "Bearer token");

		// Use reflection to call private method shouldSkipFilter
		Method method = JWTRequestFilter.class.getDeclaredMethod("shouldSkipFilter", HttpServletRequest.class);
		method.setAccessible(true);
		boolean result = (boolean) method.invoke(jwtRequestFilter, request);

		assertFalse(result); // Authorization header exists, so the filter should not be skipped
	}

	@Test
	void testExtractTokenDto() throws JsonProcessingException, Exception, SecurityException {
		// Use reflection to call private method extractTokenDto
		Method method = JWTRequestFilter.class.getDeclaredMethod("extractTokenDto", String.class);
		method.setAccessible(true);

		String jwtToken = "header.eyJzdWIiOiJ1c2VyIn0.signature";

		// Invoking private method extractTokenDto
		DecodeTokenDto result = (DecodeTokenDto) method.invoke(jwtRequestFilter, jwtToken);

		assertEquals("user", result.getSub());
	}

	@Test
	void testCreateUserDetails() throws Exception {
		// Use reflection to call private method createUserDetails
		Method method = JWTRequestFilter.class.getDeclaredMethod("createUserDetails", String.class, String.class);
		method.setAccessible(true);

		String username = "user";
		String password = "password";

		// Invoking private method createUserDetails
		UserDetails userDetails = (UserDetails) method.invoke(jwtRequestFilter, username, password);

		assertEquals("user", userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
	}

	@Test
	void testProcessJwtToken_ExpiredToken() throws Exception {
		String token = "expiredToken";
		when(jwtUtils.validateToken(token, new User("testUser", "password", new ArrayList<>())))
				.thenThrow(ExpiredJwtException.class);

		// This should throw a ServletException
		assertThrows(ServletException.class, () -> {
			jwtRequestFilter.processJwtToken(token, request);
		});
	}

}
