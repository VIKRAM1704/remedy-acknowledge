package com.hcltech.capstone.project.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.util.JWTUtils;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class JWTUtilsTest {
 
    private JWTUtils jwtUtils;
    private UserDetails userDetails;
    private Login login;
 
    @BeforeEach
    void setUp() {
        jwtUtils = new JWTUtils();
        jwtUtils.secret = "testSecret"; // Set a test secret
        userDetails = mock(UserDetails.class);
        login = new Login();
        login.setUsername("testUser");
        login.setPassword("testPassword");
    }
 
    @Test
    void testGenerateToken() {
        String token = jwtUtils.generateToken(login);
        assertNotNull(token);
    }
 
    @Test
    void testGetUsernameFromToken() {
        String token = jwtUtils.generateToken(login);
        String username = jwtUtils.getUsernameFromToken(token);
        assertEquals("testUser", username);
    }
 
    @Test
    void testGetExpirationDateFromToken() {
        String token = jwtUtils.generateToken(login);
        Date expirationDate = jwtUtils.getExpirationDateFromToken(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }
 
    @Test
    void testValidateToken() {
        String token = jwtUtils.generateToken(login);
        when(userDetails.getUsername()).thenReturn("testUser");
        assertTrue(jwtUtils.validateToken(token, userDetails));
    }

    @Test
    void testExtractUsername() {
        String token = jwtUtils.generateToken(login);
        String username = jwtUtils.extractUsername(token);
        assertEquals("testUser", username);
    }
}
 
 
