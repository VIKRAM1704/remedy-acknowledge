package com.hcltech.capstone.project.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Role;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;
import com.hcltech.capstone.project.exception.InvalidTicketStatusException;
import com.hcltech.capstone.project.exception.ResourceNotFoundException;
import com.hcltech.capstone.project.exception.TicketNotFoundException;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.repository.TicketRepository;
import com.hcltech.capstone.project.util.JWTUtils;

@Service
public class AdminService {
	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private JWTUtils jWTUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Ticket updateTicketStatus(String ticketGenerate, Ticket updatedTicket, String inProgressOrCompleted) {
	    LoginDto loginDto = UserContextHolder.getLoginDto();
	 
	    Ticket existingTicket = ticketRepository.findByTicketGenerate(ticketGenerate)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
	 if(!existingTicket.getStatus().equals(TicketStatus.CANCELLED))
	 {
	    existingTicket.setStatus(updatedTicket.getStatus());
	    existingTicket.setAcknowledgment(updatedTicket.getAcknowledgment());
	 
	    // Create admin user only once
	    Login admin = new Login();
	    admin.setUsername(loginDto.getUsername());
	    admin.setRole(Role.ADMIN);
	    admin.setActive(true);
	    admin.setPassword(loginDto.getPassword());
	    admin.setId(loginDto.getId());
	 
	    existingTicket.setUpdatedByAdmin(admin);
	 
	    if ("0".equalsIgnoreCase(inProgressOrCompleted)) {
	        existingTicket.setStatus(TicketStatus.IN_PROGRESS);
	    } else if ("1".equalsIgnoreCase(inProgressOrCompleted)) {
	        existingTicket.setStatus(TicketStatus.COMPLETED);
	        existingTicket.setCompletedDate(LocalDateTime.now());
	    } else {
	        throw new InvalidTicketStatusException(inProgressOrCompleted + " is not valid. Please provide a valid response.");
	    }
	 }
	 else
	 {
		 throw new InvalidTicketStatusException(inProgressOrCompleted + " is not valid. Cancelled ticket not allowed Please provide a valid response."); 
	 }
	 
	return ticketRepository.save(existingTicket);
	}
	 

	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	public Ticket getTicketByTicketGenerate(String ticketGenerate) {
	    return ticketRepository.findByTicketGenerate(ticketGenerate)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    String.format("Ticket not found with id: %s", ticketGenerate)
	            ));
	}

	public List<Ticket> getAllPendingTickets() {
	        return ticketRepository.findByStatus(TicketStatus.PENDING);
	}

	public List<Ticket> getAllInProgressTickets() {
		return ticketRepository.findByStatus(TicketStatus.IN_PROGRESS);
	}

	public List<Ticket> getAllCompletedTickets() {
		return ticketRepository.findByStatus(TicketStatus.COMPLETED);
	}

	public List<Ticket> getAllCancelledTickets() {
		return ticketRepository.findByStatus(TicketStatus.CANCELLED);
	}

}
