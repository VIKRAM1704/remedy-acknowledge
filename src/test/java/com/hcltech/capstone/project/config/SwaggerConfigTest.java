package com.hcltech.capstone.project.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;

import com.hcltech.capstone.project.config.SwaggerConfig;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;

class SwaggerConfigTest {

	@InjectMocks

	private SwaggerConfig swaggerConfig;

	@BeforeEach

	void setUp() {

		MockitoAnnotations.openMocks(this);

	}

	@Test

	void testCustomOpenAPI() {

		OpenAPI openAPI = swaggerConfig.customOpenAPI();

		assertNotNull(openAPI, "OpenAPI should not be null");

		assertNotNull(openAPI.getInfo(), "Info should not be null");

		Info info = openAPI.getInfo();

		assertEquals("Remedy-Aknowledgement API", info.getTitle(), "API title mismatch");

		assertEquals("1.0", info.getVersion(), "API version mismatch");

		assertEquals("Ticket Service", info.getTermsOfService(), "Terms of service mismatch");

		assertEquals("Remedy-Aknowledgement", info.getLicense().getName(), "License name mismatch");

		assertNotNull(openAPI.getComponents(), "Components should not be null");

		assertNotNull(openAPI.getComponents().getSecuritySchemes(), "Security schemes should not be null");

		SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);

		assertTrue(securityRequirement.containsKey("Authorization"), "Security scheme should contain 'Authorization'");

	}

}