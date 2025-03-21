package com.hcltech.capstone.project.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hcltech.capstone.project.dto.DecodeTokenDto;
public class DecodeTokenDtoTest {

	private DecodeTokenDto decodeTokenDto;

	@BeforeEach
	public void setUp() {
		decodeTokenDto = new DecodeTokenDto();
		decodeTokenDto.setSub("subject");
		decodeTokenDto.setIat(161718);
		decodeTokenDto.setExp(161728);
		decodeTokenDto.setRole("USER");
		decodeTokenDto.setId(12345L);
		decodeTokenDto.setUsername("testUser");
		decodeTokenDto.setPassword("testPass");
	}

	@Test
	void testGettersAndSetters() {
		// Test Sub
		assertEquals("subject", decodeTokenDto.getSub());
		decodeTokenDto.setSub("newSubject");
		assertEquals("newSubject", decodeTokenDto.getSub());

		// Test Iat
		assertEquals(161718, decodeTokenDto.getIat());
		decodeTokenDto.setIat(161719);
		assertEquals(161719, decodeTokenDto.getIat());

		// Test Exp
		assertEquals(161728, decodeTokenDto.getExp());
		decodeTokenDto.setExp(161729);
		assertEquals(161729, decodeTokenDto.getExp());

		// Test Role
		assertEquals("USER", decodeTokenDto.getRole());
		decodeTokenDto.setRole("ADMIN");
		assertEquals("ADMIN", decodeTokenDto.getRole());

		// Test Id
		assertEquals(12345L, decodeTokenDto.getId());
		decodeTokenDto.setId(12346L);
		assertEquals(12346L, decodeTokenDto.getId());

		// Test Username
		assertEquals("testUser", decodeTokenDto.getUsername());
		decodeTokenDto.setUsername("newUser");
		assertEquals("newUser", decodeTokenDto.getUsername());

		// Test Password
		assertEquals("testPass", decodeTokenDto.getPassword());
		decodeTokenDto.setPassword("newPass");
		assertEquals("newPass", decodeTokenDto.getPassword());
	}

	@Test
	void testConstructor() {
		DecodeTokenDto newDecodeTokenDto = new DecodeTokenDto();
		newDecodeTokenDto.setSub("constructorSubject");
		newDecodeTokenDto.setIat(161720);
		newDecodeTokenDto.setExp(161730);
		newDecodeTokenDto.setRole("USER");
		newDecodeTokenDto.setId(12347L);
		newDecodeTokenDto.setUsername("constructorUser");
		newDecodeTokenDto.setPassword("constructorPass");

		assertEquals("constructorSubject", newDecodeTokenDto.getSub());
		assertEquals(161720, newDecodeTokenDto.getIat());
		assertEquals(161730, newDecodeTokenDto.getExp());
		assertEquals("USER", newDecodeTokenDto.getRole());
		assertEquals(12347L, newDecodeTokenDto.getId());
		assertEquals("constructorUser", newDecodeTokenDto.getUsername());
		assertEquals("constructorPass", newDecodeTokenDto.getPassword());
	}
}