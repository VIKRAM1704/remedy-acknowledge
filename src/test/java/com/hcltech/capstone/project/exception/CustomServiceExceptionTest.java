package com.hcltech.capstone.project.exception;

import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.exception.CustomServiceException;

import static org.junit.jupiter.api.Assertions.*;	 
class CustomServiceExceptionTest {
	 
	    @Test
	 
	    void testCustomServiceExceptionWithMessageAndCause() {
	        String message = "Service error occurred";
	        Throwable cause = new Throwable("Cause of the error");
	        CustomServiceException exception = new CustomServiceException(message, cause);
	        assertEquals(message, exception.getMessage());
	 
	        assertEquals(cause, exception.getCause());
	 
	    }
	 
	    @Test
	 
	    void testCustomServiceExceptionWithOnlyMessage() {
	        String message = "Service error occurred";
	        CustomServiceException exception = new CustomServiceException(message, null);
	        assertEquals(message, exception.getMessage());
	        assertNull(exception.getCause());
	    }
	 
	    @Test
	 
	    void testCustomServiceExceptionWithNullMessageAndCause() {
	        CustomServiceException exception = new CustomServiceException(null, null);
	        assertNull(exception.getMessage());
	        assertNull(exception.getCause());
	 
	    }
	 
	 
}
