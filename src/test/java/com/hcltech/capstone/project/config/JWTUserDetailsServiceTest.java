package com.hcltech.capstone.project.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.hcltech.capstone.project.config.JWTUserDetailsService;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.repository.LoginRepository;

public class JWTUserDetailsServiceTest {
	@InjectMocks
	private JWTUserDetailsService jwtUserDetailsService;

	@Mock
	private LoginRepository loginRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoadUserByUsername_Success() {
		String username = "testUser";
		Login login = new Login();
		login.setUsername(username);
		login.setPassword("password");

		when(loginRepository.findByUsername(username)).thenReturn(Optional.of(login));

		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

		assertNotNull(userDetails);
		assertEquals(username, userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
	}

	@Test
	void testLoadUserByUsername_UserNotFound() {
		String username = "testUser";

		when(loginRepository.findByUsername(username)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			jwtUserDetailsService.loadUserByUsername(username);
		});
	}

	@Test
	void testGetLogin_Success() {
		String username = "testUser";
		Login login = new Login();
		login.setUsername(username);
		login.setPassword("password");

		when(loginRepository.findByUsername(username)).thenReturn(Optional.of(login));

		Login result = jwtUserDetailsService.getLogin(username);

		assertNotNull(result);
		assertEquals(username, result.getUsername());
		assertEquals("password", result.getPassword());
	}

	@Test
	void testGetLogin_UserNotFound() {
		String username = "testUser";

		when(loginRepository.findByUsername(username)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			jwtUserDetailsService.getLogin(username);
		});
	}
}
