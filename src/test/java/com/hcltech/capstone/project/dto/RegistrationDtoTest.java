package com.hcltech.capstone.project.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.dto.RegistrationDto;

public class RegistrationDtoTest {

	@Test
	void testConstructorAndGetters() {
		RegistrationDto dto = new RegistrationDto("user123", "pass1234", "ADMIN");

		assertEquals("user123", dto.getUsername());
		assertEquals("pass1234", dto.getPassword());
		assertEquals("ADMIN", dto.getRole());
	}

	@Test
	void testSetters() {
		RegistrationDto dto = new RegistrationDto();
		dto.setUsername("user123");
		dto.setPassword("pass1234");
		dto.setRole("ADMIN");

		assertEquals("user123", dto.getUsername());
		assertEquals("pass1234", dto.getPassword());
		assertEquals("ADMIN", dto.getRole());
	}
}