package com.hcltech.capstone.project.service;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Role;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;
import com.hcltech.capstone.project.exception.ResourceNotFoundException;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
 
    @Mock
    private TicketRepository ticketRepository;
 
    @Mock
    private LoginRepository loginRepository;
 
    @Mock
    private PasswordEncoder passwordEncoder;
 
    @InjectMocks
    private UserService userService;
 
    private Ticket ticket;
    private LoginDto loginDto;
    private Login login;
 
    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTicketGenerate("TICKET123");
        ticket.setStatus(TicketStatus.PENDING);
 
        loginDto = new LoginDto();
        loginDto.setId((int) 1L);
        loginDto.setUsername("user");
        loginDto.setPassword("password");
        loginDto.setRole("USER");
        loginDto.setActive(true);
 
        login = new Login();
        login.setId((int) 1L);
        login.setUsername("user");
        login.setPassword("password");
        login.setRole(Role.USER);
        login.setActive(true);
        login.setTickets(Collections.singletonList(ticket));
 
        UserContextHolder.setLoginDto(loginDto);
    }
 
 
 
    @Test
     void testRaiseTicket_UserNotActive() {
        
        loginDto.setActive(false);
        UserContextHolder.setLoginDto(loginDto);
 
        
        Ticket result = userService.raiseTicket(ticket);
 
        
        assertNull(result.getTicketGenerate());
        assertNull(result.getUser());
    }
 
    @Test
     void testGetTicketByTicketGenerate() {
       
        when(loginRepository.findByUsername("user")).thenReturn(Optional.of(login));
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.of(ticket));
 
       
        Ticket result = userService.getTicketByTicketGenerate("TICKET123");
 
        
        assertNotNull(result);
        assertEquals("TICKET123", result.getTicketGenerate());
        verify(ticketRepository, times(1)).findByTicketGenerate("TICKET123");
    }
 
    @Test
    void testGetTicketByTicketGenerate_TicketNotFound() {
        
        when(loginRepository.findByUsername("user")).thenReturn(Optional.of(login));
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.empty());
 
        
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getTicketByTicketGenerate("TICKET123");
        });
 
        assertEquals("Ticket is not found in this user Username.", exception.getMessage());
    }
 
    
 
    @Test
    void testGetTicketByUsername() {
       
        when(loginRepository.findByUsername("user")).thenReturn(Optional.of(login));
 
        
        List<Ticket> result = userService.getTicketByUsername();
 
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loginRepository, times(1)).findByUsername("user");
    }
 
    @Test
    void testGetTicketByUsername_UserNotActive() {
       
        loginDto.setActive(false);
        UserContextHolder.setLoginDto(loginDto);
 
        
        List<Ticket> result = userService.getTicketByUsername();
 
       
        assertNull(result);
    }
 
    @Test
    void testUpdateUserCancelledStatus() {
       
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
 
       
        Ticket result = userService.updateUserCancelledStatus("TICKET123", "User cancelled");
 
        
        assertNotNull(result);
        assertEquals(TicketStatus.CANCELLED, result.getStatus());
        assertEquals("User cancelled", result.getComment());
        verify(ticketRepository, times(1)).findByTicketGenerate("TICKET123");
        verify(ticketRepository, times(1)).save(ticket);
    }
 
    @Test
    void testUpdateUserCancelledStatus_TicketNotFound() {
        
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.empty());
 
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUserCancelledStatus("TICKET123", "User cancelled");
        });
 
        assertEquals("Ticket not found", exception.getMessage());
    }
    
    @Test
    void testRaiseTicket_Success() {
        // Arrange
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
 
        // Act
        Ticket result = userService.raiseTicket(ticket);
 
        // Assert
        assertNotNull(result);
        assertNotNull(result.getTicketGenerate());
        assertTrue(result.getTicketGenerate().startsWith("TICKET"));
        assertNotNull(result.getUser());
        assertEquals(loginDto.getUsername(), result.getUser().getUsername());
        assertEquals(Role.USER, result.getUser().getRole());
        verify(ticketRepository, times(1)).save(ticket);
    }
 
    @Test
    void testRaiseTicket_UserNotActive1() {
        // Arrange
        loginDto.setActive(false); // Simulate inactive user
        UserContextHolder.setLoginDto(loginDto);
 
        // Act
        Ticket result = userService.raiseTicket(ticket);
 
        // Assert
        assertNotNull(result);
        assertNull(result.getTicketGenerate());
        assertNull(result.getUser());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
 
}
 