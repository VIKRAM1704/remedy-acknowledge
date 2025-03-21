package com.hcltech.capstone.project.controller;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;
import com.hcltech.capstone.project.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
 
    @Mock
    private AdminService adminService;
 
    @InjectMocks
    private AdminController adminController;
 
    private MockMvc mockMvc;
 
    private Ticket ticket;
    private LoginDto loginDto;
 
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
 
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
    void testUpdateTicketStatus() throws Exception {
       
        when(adminService.updateTicketStatus(anyString(), any(Ticket.class), anyString())).thenReturn(ticket);
 
        
        mockMvc.perform(put("/api/admin/123/status")
                .param("inProgressis0Completedis1", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
 
    @Test
    void testGetAllTickets() throws Exception {
       
        List<Ticket> tickets = Arrays.asList(ticket);
        when(adminService.getAllTickets()).thenReturn(tickets);
 
       
        mockMvc.perform(get("/api/admin/get_alltickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ticketGenerate").value("TICKET123"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
 
    @Test
    void testGetTicketsByUsername_AdminRole() throws Exception {
        
        when(adminService.getTicketByTicketGenerate("TICKET123")).thenReturn(ticket);
 
        
        mockMvc.perform(get("/api/admin/tickets/123")
                .param("ticketGenerate", "TICKET123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ticketGenerate").value("TICKET123"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
 
    @Test
    void testGetTicketsByUsername_UnauthorizedRole() throws Exception {
        
        loginDto.setRole("USER");
        UserContextHolder.setLoginDto(loginDto);
 
       
        mockMvc.perform(get("/api/admin/tickets/123")
                .param("ticketGenerate", "TICKET123"))
                .andExpect(status().isUnauthorized());
    }
 
    @Test
    void testGetAllPendingTickets() throws Exception {
        
        List<Ticket> pendingTickets = Arrays.asList(ticket);
        when(adminService.getAllPendingTickets()).thenReturn(pendingTickets);
 
        
        mockMvc.perform(get("/api/admin/tickets/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ticketGenerate").value("TICKET123"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
 
    @Test
    void testGetAllInProgressTickets() throws Exception {
        
        List<Ticket> inProgressTickets = Arrays.asList(ticket);
        when(adminService.getAllInProgressTickets()).thenReturn(inProgressTickets);
 
       
        mockMvc.perform(get("/api/admin/tickets/in progress"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ticketGenerate").value("TICKET123"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
 
    @Test
    void testGetAllCompletedTickets() throws Exception {
        
        List<Ticket> completedTickets = Arrays.asList(ticket);
        when(adminService.getAllCompletedTickets()).thenReturn(completedTickets);
 
        
        mockMvc.perform(get("/api/admin/tickets/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ticketGenerate").value("TICKET123"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
 
    @Test
    void testGetAllCancelledTickets() throws Exception {
        
        List<Ticket> cancelledTickets = Arrays.asList(ticket);
        when(adminService.getAllCancelledTickets()).thenReturn(cancelledTickets);
 
       
        mockMvc.perform(get("/api/admin/tickets/cancelled"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ticketGenerate").value("TICKET123"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));
    }
}