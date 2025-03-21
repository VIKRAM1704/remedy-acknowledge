package com.hcltech.capstone.project.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Role;
import com.hcltech.capstone.project.entity.Ticket;

public class LoginTest {

	    private Login login;
	    private List<Ticket> tickets;

	    @BeforeEach
	    public void setUp() {
	        tickets = new ArrayList<>();
	        login = new Login(1, "username", "password", Role.USER, true, tickets);
	    }

	    @Test
	    	void testGettersAndSetters() {
	        // Test ID
	        login.setId(2);
	        assertEquals(2, login.getId());

	        // Test Username
	        login.setUsername("newUsername");
	        assertEquals("newUsername", login.getUsername());

	        // Test Password
	        login.setPassword("newPassword");
	        assertEquals("newPassword", login.getPassword());

	        // Test Role
	        login.setRole(Role.ADMIN);
	        assertEquals(Role.ADMIN, login.getRole());

	        // Test isActive
	        login.setActive(false);
	        assertFalse(login.isActive());

	        // Test Tickets
	        List<Ticket> newTickets = new ArrayList<>();
	        login.setTickets(newTickets);
	        assertEquals(newTickets, login.getTickets());
	    }

	    @Test
	    	void testConstructor() {
	        Login newLogin = new Login(3, "testUser", "testPass", Role.USER, true, tickets);
	        assertEquals(3, newLogin.getId());
	        assertEquals("testUser", newLogin.getUsername());
	        assertEquals("testPass", newLogin.getPassword());
	        assertEquals(Role.USER, newLogin.getRole());
	        assertTrue(newLogin.isActive());
	        assertEquals(tickets, newLogin.getTickets());
	    }
	
}
