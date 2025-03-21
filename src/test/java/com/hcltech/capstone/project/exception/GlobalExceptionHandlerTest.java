package com.hcltech.capstone.project.exception;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcltech.capstone.project.exception.GlobalExceptionHandler;
import com.hcltech.capstone.project.exception.ResourceNotFoundException;
 
@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest
{
	
	 private GlobalExceptionHandler globalExceptionHandler;
	
	 @BeforeEach
	    public void setUp() {
	        globalExceptionHandler = new GlobalExceptionHandler();
	    }
 
	    @Test
	    	void testHandleResourceNotFoundException() {
	        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
	        ResponseEntity<String> response = globalExceptionHandler.handleResourceNotFoundException(ex);
 
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }
 
	    @Test
	    	void testHandleException() {
	        Exception ex = new Exception("Internal server error");
	        ResponseEntity<String> response = globalExceptionHandler.handleException(ex);
 
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	        assertEquals("Internal server error", response.getBody());
	    }
}
