package com.hcltech.capstone.project.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.dto.LoginRequestDto;

public class LoginRequestDtoTest {

	private LoginRequestDto loginRequestDto;

	@BeforeEach
	public void setUp() {
		loginRequestDto = new LoginRequestDto(null, null);
		loginRequestDto.setUserName("squad_6");
		loginRequestDto.setPassword("P@ssw0rd");
	}

	@Test
	void testGettersAndSetters() {
		// Test Username
		assertEquals("squad_6", loginRequestDto.getUserName());
		loginRequestDto.setUserName("newUser");
		assertEquals("newUser", loginRequestDto.getUserName());

		// Test Password
		assertEquals("P@ssw0rd", loginRequestDto.getPassword());
		loginRequestDto.setPassword("newPass");
		assertEquals("newPass", loginRequestDto.getPassword());
	}

	@Test
	void testConstructor() {
		LoginRequestDto newLoginRequestDto = new LoginRequestDto(null, null);
		newLoginRequestDto.setUserName("constructorUser");
		newLoginRequestDto.setPassword("constructorPass");

		assertEquals("constructorUser", newLoginRequestDto.getUserName());
		assertEquals("constructorPass", newLoginRequestDto.getPassword());
	}
}
