package com.hcltech.capstone.project.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;

public class TicketTest {
	   private Ticket ticket;
	    private LocalDateTime createdDate;
	    private LocalDateTime completedDate;
	 
	    @BeforeEach
	    public void setUp() {
	        createdDate = LocalDateTime.now();
	        completedDate = LocalDateTime.now().plusDays(1);
	        ticket = new Ticket(1L, "Sample Title", "Sample Description", createdDate, TicketStatus.PENDING, null, null, "In Progress", "Sample Comment", completedDate, null);
	    }
	 
	    @Test
	    	void testConstructorAndGetters() {
	        LocalDateTime now = LocalDateTime.now();
	        Login user = new Login();
	        Login admin = new Login();
	        Ticket ticket = new Ticket(1L, "Title", "Description", now, TicketStatus.PENDING, user, admin, "Comment", "Acknowledgment", now, "TicketGenerate");

	        assertEquals(1L, ticket.getId());
	        assertEquals("Title", ticket.getTitle());
	        assertEquals("Description", ticket.getDescription());
	        assertEquals(now, ticket.getCreatedDate());
	        assertEquals(TicketStatus.PENDING, ticket.getStatus());
	        assertEquals(user, ticket.getUser());
	        assertEquals(admin, ticket.getUpdatedByAdmin());
	        assertEquals("Comment", ticket.getComment());
	        assertEquals("Acknowledgment", ticket.getAcknowledgment());
	        assertEquals(now, ticket.getCompletedDate());
	        assertEquals("TicketGenerate", ticket.getTicketGenerate());
	    }

	    @Test
	    	void testSetters() {
	        LocalDateTime now = LocalDateTime.now();
	        Login user = new Login();
	        Login admin = new Login();
	        Ticket ticket = new Ticket();

	        ticket.setId(1L);
	        ticket.setTitle("Title");
	        ticket.setDescription("Description");
	        ticket.setCreatedDate(now);
	        ticket.setStatus(TicketStatus.PENDING);
	        ticket.setUser(user);
	        ticket.setUpdatedByAdmin(admin);
	        ticket.setComment("Comment");
	        ticket.setAcknowledgment("Acknowledgment");
	        ticket.setCompletedDate(now);
	        ticket.setTicketGenerate("TicketGenerate");

	        assertEquals(1L, ticket.getId());
	        assertEquals("Title", ticket.getTitle());
	        assertEquals("Description", ticket.getDescription());
	        assertEquals(now, ticket.getCreatedDate());
	        assertEquals(TicketStatus.PENDING, ticket.getStatus());
	        assertEquals(user, ticket.getUser());
	        assertEquals(admin, ticket.getUpdatedByAdmin());
	        assertEquals("Comment", ticket.getComment());
	        assertEquals("Acknowledgment", ticket.getAcknowledgment());
	        assertEquals(now, ticket.getCompletedDate());
	        assertEquals("TicketGenerate", ticket.getTicketGenerate());
	    }
	    
	    @Test
	    	void testPrePersistWithNullStatus() {
	        Ticket ticket = new Ticket();
	        ticket.prePersist();

	        assertEquals(TicketStatus.PENDING, ticket.getStatus());
	        assertNotNull(ticket.getCreatedDate());
	    }

	    @Test
	    	void testPrePersistWithNonNullStatus() {
	        Ticket ticket = new Ticket();
	        ticket.setStatus(TicketStatus.COMPLETED);
	        ticket.prePersist();

	        assertEquals(TicketStatus.COMPLETED, ticket.getStatus());
	        assertNotNull(ticket.getCreatedDate());
	    }

	    @Test
	    	void testPrePersistSetsCreatedDate() {
	        Ticket ticket = new Ticket();
	        ticket.prePersist();

	        LocalDateTime now = LocalDateTime.now();
	        assertNotNull(ticket.getCreatedDate());
	        assertTrue(ticket.getCreatedDate().isBefore(now.plusSeconds(1)));
	        assertTrue(ticket.getCreatedDate().isAfter(now.minusSeconds(1)));
	    }
}
