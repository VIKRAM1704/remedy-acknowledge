package com.hcltech.capstone.project.exception;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.exception.UserNotFoundException;
 
class UserNotFoundExceptionTest {
 
    @Test
    void testUserNotFoundExceptionMessage() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);
 
        assertEquals(message, exception.getMessage(), "Exception message should match");
    }
}