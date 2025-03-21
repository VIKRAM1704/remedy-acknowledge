package com.hcltech.capstone.project.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidTicketStatusExceptionTest {
	@Test
	   void testInvalidTicketStatusExceptionMessage() {
	       String message = "ivalid ticket status";
	       InvalidTicketStatusException exception = new InvalidTicketStatusException(message);

	       assertEquals(message, exception.getMessage(), "Exception message should match");
	   }
}
