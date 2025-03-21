package com.hcltech.capstone.project.service; 
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
 
    @Mock
    private TicketRepository ticketRepository;
 
    @Mock
    private LoginRepository loginRepository;
 
    @InjectMocks
    private AdminService adminService;
 
    private Ticket ticket;
    private LoginDto loginDto;
 
    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTicketGenerate("TICKET123");
        ticket.setStatus(TicketStatus.PENDING);
 
        loginDto = new LoginDto();
        loginDto.setId((int) 1L);
        loginDto.setUsername("admin");
        loginDto.setPassword("password");
        loginDto.setRole("ADMIN");
 
        UserContextHolder.setLoginDto(loginDto);
    }
 
    @Test
    void testUpdateTicketStatus_Success_InProgress() {
        Ticket updatedTicket = new Ticket();
        updatedTicket.setStatus(TicketStatus.IN_PROGRESS);
        updatedTicket.setAcknowledgment("Acknowledged");
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        Ticket result = adminService.updateTicketStatus("TICKET123", updatedTicket, "0");
        assertNotNull(result);
        assertEquals(TicketStatus.IN_PROGRESS, result.getStatus());
        assertEquals("Acknowledged", result.getAcknowledgment());
        assertNotNull(result.getUpdatedByAdmin());
        verify(ticketRepository, times(1)).findByTicketGenerate("TICKET123");
        verify(ticketRepository, times(1)).save(ticket);
    }
 
    @Test
    void testUpdateTicketStatus_Success_Completed() {
        Ticket updatedTicket = new Ticket();
        updatedTicket.setStatus(TicketStatus.COMPLETED);
        updatedTicket.setAcknowledgment("Acknowledged");
 
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        Ticket result = adminService.updateTicketStatus("TICKET123", updatedTicket, "1");
        assertNotNull(result);
        assertEquals(TicketStatus.COMPLETED, result.getStatus());
        assertEquals("Acknowledged", result.getAcknowledgment());
        assertNotNull(result.getUpdatedByAdmin());
        assertNotNull(result.getCompletedDate());
        verify(ticketRepository, times(1)).findByTicketGenerate("TICKET123");
        verify(ticketRepository, times(1)).save(ticket);
    }
    @Test
    void testGetAllTickets() {
       
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);
        List<Ticket> result = adminService.getAllTickets();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findAll();
    }
 
    @Test
    void testGetTicketByTicketGenerate() {
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.of(ticket));
        Ticket result = adminService.getTicketByTicketGenerate("TICKET123");
        assertNotNull(result);
        assertEquals("TICKET123", result.getTicketGenerate());
        verify(ticketRepository, times(1)).findByTicketGenerate("TICKET123");
    }
 
    @Test
    void testGetTicketByTicketGenerate_NotFound() {
        when(ticketRepository.findByTicketGenerate("TICKET123")).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            adminService.getTicketByTicketGenerate("TICKET123");
        });
 
        assertEquals("Ticket not found with id: TICKET123", exception.getMessage());
    }
 
    @Test
    void testGetAllPendingTickets() {
        List<Ticket> pendingTickets = Arrays.asList(ticket);
        when(ticketRepository.findByStatus(TicketStatus.PENDING)).thenReturn(pendingTickets);
        List<Ticket> result = adminService.getAllPendingTickets();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findByStatus(TicketStatus.PENDING);
    }
 
    @Test
    void testGetAllInProgressTickets() {
        List<Ticket> inProgressTickets = Arrays.asList(ticket);
        when(ticketRepository.findByStatus(TicketStatus.IN_PROGRESS)).thenReturn(inProgressTickets);
        List<Ticket> result = adminService.getAllInProgressTickets();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findByStatus(TicketStatus.IN_PROGRESS);
    }
 
    @Test
    void testGetAllCompletedTickets() {
       
        List<Ticket> completedTickets = Arrays.asList(ticket);
        when(ticketRepository.findByStatus(TicketStatus.COMPLETED)).thenReturn(completedTickets);
        List<Ticket> result = adminService.getAllCompletedTickets();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findByStatus(TicketStatus.COMPLETED);
    }
 
    @Test
    void testGetAllCancelledTickets() {
       
        List<Ticket> cancelledTickets = Arrays.asList(ticket);
        when(ticketRepository.findByStatus(TicketStatus.CANCELLED)).thenReturn(cancelledTickets);
        List<Ticket> result = adminService.getAllCancelledTickets();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findByStatus(TicketStatus.CANCELLED);
    }
}