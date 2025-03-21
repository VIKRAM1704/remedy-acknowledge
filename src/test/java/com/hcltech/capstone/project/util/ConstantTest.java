package com.hcltech.capstone.project.util;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.util.Constant;

import static org.junit.jupiter.api.Assertions.assertEquals;
 
class ConstantTest {
 
    @Test
    void testConstants() {
        assertEquals("Bearer ", Constant.BEARER);
        assertEquals("Access Token is invalid", Constant.ACCESS_TOKEN_IS_INVALID);
        assertEquals("Access Token has expired", Constant.ACCESS_TOKEN_HAS_EXPIRED);
        assertEquals("INVALID_CREDENTIALS", Constant.INVALID_CREDENTIALS);
        assertEquals("Registration Successful", Constant.REGISTRATION_SUCCESS_FULL);
        assertEquals("Authorization", Constant.AUTHORIZATION);
        assertEquals("UserName Not Found", Constant.USER_NAME_NOT_FOUND);
    }
}