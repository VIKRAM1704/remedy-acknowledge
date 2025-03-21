package com.hcltech.capstone.project.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.exception.UserAlreadyExistsException;

class UserAlreadyExistsExceptionTest {

   @Test
   void testUserAlreadyExistsExceptionMessage() {
       String message = "User already exists";
       UserAlreadyExistsException exception = new UserAlreadyExistsException(message);

       assertEquals(message, exception.getMessage(), "Exception message should match");
   }
}