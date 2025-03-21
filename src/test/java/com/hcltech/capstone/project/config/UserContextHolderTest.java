package com.hcltech.capstone.project.config;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
class UserContextHolderTest {

	private LoginDto loginDto;

	@BeforeEach

	void setUp() {

		loginDto = new LoginDto();

		loginDto.setRole("ADMIN");

		loginDto.setUsername("adminUser");

	}

	@Test

	void testSetAndGetLoginDto() {

		UserContextHolder.setLoginDto(loginDto);

		LoginDto result = UserContextHolder.getLoginDto();

		assertNotNull(result, "LoginDto should not be null");

		assertEquals("ADMIN", result.getRole(), "Role should match");

		assertEquals("adminUser", result.getUsername(), "Username should match");

	}

	@Test

	void testClear() {

		UserContextHolder.setLoginDto(loginDto);

		UserContextHolder.clear();

		assertNull(UserContextHolder.getLoginDto(), "LoginDto should be null after clear");

	}

}
