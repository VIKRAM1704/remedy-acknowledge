package com.hcltech.capstone.project.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.dto.PasswordChangeRequest;

public class PasswordChangeRequestTest {

	private PasswordChangeRequest passwordChangeRequest;

	@BeforeEach
	public void setUp() {
		passwordChangeRequest = new PasswordChangeRequest();
		passwordChangeRequest.setAdminId(1L);
		passwordChangeRequest.setUserId(2L);
		passwordChangeRequest.setNewPassword("newPassword");
	}

	@Test
	void testGettersAndSetters() {
		// Test AdminId
		assertEquals(1L, passwordChangeRequest.getAdminId());
		passwordChangeRequest.setAdminId(3L);
		assertEquals(3L, passwordChangeRequest.getAdminId());

		// Test UserId
		assertEquals(2L, passwordChangeRequest.getUserId());
		passwordChangeRequest.setUserId(4L);
		assertEquals(4L, passwordChangeRequest.getUserId());

		// Test NewPassword
		assertEquals("newPassword", passwordChangeRequest.getNewPassword());
		passwordChangeRequest.setNewPassword("updatedPassword");
		assertEquals("updatedPassword", passwordChangeRequest.getNewPassword());
	}

	@Test
	void testConstructor() {
		PasswordChangeRequest newPasswordChangeRequest = new PasswordChangeRequest();
		newPasswordChangeRequest.setAdminId(5L);
		newPasswordChangeRequest.setUserId(6L);
		newPasswordChangeRequest.setNewPassword("constructorPassword");

		assertEquals(5L, newPasswordChangeRequest.getAdminId());
		assertEquals(6L, newPasswordChangeRequest.getUserId());
		assertEquals("constructorPassword", newPasswordChangeRequest.getNewPassword());
	}
}