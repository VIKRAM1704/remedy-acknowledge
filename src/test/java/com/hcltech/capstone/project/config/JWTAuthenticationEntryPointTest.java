package com.hcltech.capstone.project.config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.PrintWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;

import com.hcltech.capstone.project.config.JWTAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationEntryPointTest {
	@InjectMocks
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private AuthenticationException authException;

	@Mock
	private PrintWriter writer;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		when(response.getWriter()).thenReturn(writer);
	}

	@Test
	void testCommence() throws Exception {
		jwtAuthenticationEntryPoint.commence(request, response, authException);

		verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		verify(response).setContentType("application/json");
		verify(writer).write("{\"error\": \"Unauthorized Access\"}");
		verify(writer).flush();
		verify(writer).close();
	}
}
