package com.hcltech.capstone.project.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TicketNotFoundExceptionTest {
	@Test
	   void testTicketNotFoundExceptionMessage() {
	       String message = " ticket not found";
	       TicketNotFoundException exception = new TicketNotFoundException(message);

	       assertEquals(message, exception.getMessage(), "Exception message should match");
	   }
}
