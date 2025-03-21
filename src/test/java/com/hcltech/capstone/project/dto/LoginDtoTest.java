package com.hcltech.capstone.project.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.dto.LoginDto;

public class LoginDtoTest {
	private LoginDto loginDto;

	@BeforeEach
	public void setUp() {
		loginDto = new LoginDto();
		loginDto.setId(1);
		loginDto.setUsername("testUser");
		loginDto.setPassword("testPass");
		loginDto.setRole("USER");
		loginDto.setToken("testToken");
		loginDto.setActive(true);
		loginDto.setCreatedAt(LocalDate.of(2023, 1, 1));
		loginDto.setUpdatedAt(LocalDate.of(2023, 1, 2));
	}

	@Test
	void testGettersAndSetters() {
		// Test ID
		assertEquals(1, loginDto.getId());

		// Test Username
		loginDto.setUsername("newUser");
		assertEquals("newUser", loginDto.getUsername());

		// Test Password
		loginDto.setPassword("newPass");
		assertEquals("newPass", loginDto.getPassword());

		// Test Role
		loginDto.setRole("ADMIN");
		assertEquals("ADMIN", loginDto.getRole());

		// Test Token
		loginDto.setToken("newToken");
		assertEquals("newToken", loginDto.getToken());

		// Test isActive
		loginDto.setActive(false);
		assertFalse(loginDto.isActive());

		// Test CreatedAt
		LocalDate newCreatedAt = LocalDate.of(2023, 2, 1);
		loginDto.setCreatedAt(newCreatedAt);
		assertEquals(newCreatedAt, loginDto.getCreatedAt());

		// Test UpdatedAt
		LocalDate newUpdatedAt = LocalDate.of(2023, 2, 2);
		loginDto.setUpdatedAt(newUpdatedAt);
		assertEquals(newUpdatedAt, loginDto.getUpdatedAt());
	}

	@Test
	void testConstructor() {
		LoginDto newLoginDto = new LoginDto();
		newLoginDto.setId(2);
		newLoginDto.setUsername("constructorUser");
		newLoginDto.setPassword("constructorPass");
		newLoginDto.setRole("USER");
		newLoginDto.setToken("constructorToken");
		newLoginDto.setActive(true);
		newLoginDto.setCreatedAt(LocalDate.of(2023, 3, 1));
		newLoginDto.setUpdatedAt(LocalDate.of(2023, 3, 2));

		assertEquals(2, newLoginDto.getId());
		assertEquals("constructorUser", newLoginDto.getUsername());
		assertEquals("constructorPass", newLoginDto.getPassword());
		assertEquals("USER", newLoginDto.getRole());
		assertEquals("constructorToken", newLoginDto.getToken());
		assertTrue(newLoginDto.isActive());
		assertEquals(LocalDate.of(2023, 3, 1), newLoginDto.getCreatedAt());
		assertEquals(LocalDate.of(2023, 3, 2), newLoginDto.getUpdatedAt());
	}

}
