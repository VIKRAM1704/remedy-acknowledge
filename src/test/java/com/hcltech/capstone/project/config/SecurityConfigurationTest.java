package com.hcltech.capstone.project.config;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hcltech.capstone.project.config.JWTAuthenticationEntryPoint;
import com.hcltech.capstone.project.config.JWTRequestFilter;
import com.hcltech.capstone.project.config.JWTUserDetailsService;
import com.hcltech.capstone.project.config.SecurityConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("removal")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigurationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private JWTRequestFilter jwtRequestFilter;

	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@InjectMocks
	private SecurityConfiguration securityConfiguration;
	@Mock
	private JWTUserDetailsService jwtUserDetailsService;

	@BeforeEach

	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();

		MockitoAnnotations.openMocks(this);

	}

	@Test

	void testPasswordEncoder() {

		PasswordEncoder encoder = securityConfiguration.passwordEncoder();

		assertNotNull(encoder);

		assertTrue(encoder instanceof BCryptPasswordEncoder);

	}

	@Test

	void testAuthenticationProvider() {

		AuthenticationProvider provider = securityConfiguration.authenticationProvider(jwtUserDetailsService);

		assertNotNull(provider);

		assertTrue(provider instanceof DaoAuthenticationProvider);

	}

	@Test

	void testAuthenticationManager() throws Exception {

		HttpSecurity httpSecurity = mock(HttpSecurity.class);

		AuthenticationManagerBuilder authManagerBuilder = mock(AuthenticationManagerBuilder.class);

		when(httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)).thenReturn(authManagerBuilder);

		when(authManagerBuilder.build()).thenReturn(mock(AuthenticationManager.class));

		AuthenticationManager manager = securityConfiguration.authenticationManager(httpSecurity);

		assertNotNull(manager);

	}

	@Test
	void whenAccessPublicEndpoints_thenSuccess() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk());

		mockMvc.perform(get("/register")).andExpect(status().isOk());

		mockMvc.perform(get("/swagger-ui.html")).andExpect(status().isOk());

		mockMvc.perform(get("/swagger-us/some-path")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void whenAccessProtectedEndpointWithAuth_thenSuccess() throws Exception {
		mockMvc.perform(get("/protected-endpoint")).andExpect(status().isOk());
	}

}
