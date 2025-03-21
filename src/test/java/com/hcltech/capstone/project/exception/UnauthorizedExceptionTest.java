package com.hcltech.capstone.project.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnauthorizedExceptionTest {
	@Test
	   void testUnauthorizedExceptionMessage() {
	       String message = " unauthorized exception";
	       UnauthorizedException exception = new UnauthorizedException(message);

	       assertEquals(message, exception.getMessage(), "Exception message should match");
	   }
}
