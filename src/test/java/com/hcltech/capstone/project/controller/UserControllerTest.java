package com.hcltech.capstone.project.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.controller.UserController;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;
import com.hcltech.capstone.project.exception.ResourceNotFoundException;
import com.hcltech.capstone.project.service.UserService;

@ExtendWith(MockitoExtension.class)

class UserControllerTest {
	@InjectMocks

	private UserController userController;
	@Mock

	private UserService userService;
	@Mock

	private LoginDto loginDto;

	@BeforeEach

	void setUp() {

		MockitoAnnotations.openMocks(this);

	}

	@Test

	void testRaiseTicket_Success() {

		Ticket ticket = new Ticket();

		ticket.setId(1L);

		ticket.setDescription("Test Ticket");
		try (MockedStatic<UserContextHolder> mockedStatic = mockStatic(UserContextHolder.class)) {

			mockedStatic.when(UserContextHolder::getLoginDto).thenReturn(loginDto);

			when(loginDto.getRole()).thenReturn("USER");

			when(userService.raiseTicket(any(Ticket.class))).thenReturn(ticket);
			ResponseEntity<Ticket> response = userController.raiseTicket(ticket);

			assertEquals(200, response.getStatusCodeValue());

			assertEquals(ticket, response.getBody());

		}

	}

	@Test

	void testRaiseTicket_Unauthorized() {

		Ticket ticket = new Ticket();
		try (MockedStatic<UserContextHolder> mockedStatic = mockStatic(UserContextHolder.class)) {

			mockedStatic.when(UserContextHolder::getLoginDto).thenReturn(loginDto);

			when(loginDto.getRole()).thenReturn("ADMIN");
			ResponseEntity<Ticket> response = userController.raiseTicket(ticket);

			assertEquals(401, response.getStatusCodeValue());

		}

	}

	@Test
	public void testGetTicketById_Found() {
		// Arrange
		String ticketGenerate = "12345";
		Ticket mockTicket = new Ticket();
		mockTicket.setTicketGenerate(ticketGenerate);
		when(userService.getTicketByTicketGenerate(ticketGenerate)).thenReturn(mockTicket);
		// Act
		ResponseEntity<Ticket> response = userController.getTicketByTicketGenerate(ticketGenerate);
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(ticketGenerate, response.getBody().getTicketGenerate());
	}

	@Test
	public void testGetTicketById_NotFound1() {
		// Arrange
		String ticketGenerate = "67890";
		when(userService.getTicketByTicketGenerate(ticketGenerate))
				.thenThrow(new ResourceNotFoundException("Ticket is not found"));
		// Act & Assert
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			userController.getTicketByTicketGenerate(ticketGenerate);
		});
		assertEquals("Ticket is not found", exception.getMessage());
	}
	
	@Test
    void testUpdateUserCancelledStatus_Success() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTicketGenerate("TICKET123");
        ticket.setStatus(TicketStatus.CANCELLED);
     
        try (MockedStatic<UserContextHolder> mockedStatic = mockStatic(UserContextHolder.class)) {
            mockedStatic.when(UserContextHolder::getLoginDto).thenReturn(loginDto);
            when(loginDto.getRole()).thenReturn("USER");
     
            when(userService.updateUserCancelledStatus(anyString(), anyString())).thenReturn(ticket);
     
            ResponseEntity<Ticket> response = userController.updateUserCancelledStatus("TICKET123", "Cancelled by user");
     
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(TicketStatus.CANCELLED, response.getBody().getStatus());
            verify(userService, times(1)).updateUserCancelledStatus(anyString(), anyString());
        }
    }
     
    @Test
    void testUpdateUserCancelledStatus_Unauthorized() {
        try (MockedStatic<UserContextHolder> mockedStatic = mockStatic(UserContextHolder.class)) {
            mockedStatic.when(UserContextHolder::getLoginDto).thenReturn(loginDto);
            when(loginDto.getRole()).thenReturn("ADMIN");
     
            ResponseEntity<Ticket> response = userController.updateUserCancelledStatus("TICKET123", "Cancelled by user");
     
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
            verify(userService, never()).updateUserCancelledStatus(anyString(), anyString());
        }
    }

 
    @Test
    public void testGetTicketByUsername_NoTicketsFound() {
       
        when(userService.getTicketByUsername()).thenReturn(null);
 
       
        ResponseEntity<List<Ticket>> response = userController.getTicketByUsername();
 
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getTicketByUsername();
    }

}