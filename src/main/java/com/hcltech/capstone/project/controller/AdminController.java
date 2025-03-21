package com.hcltech.capstone.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Management", description = "Endpoints for managing admin-related operations")
public class AdminController {
	@Autowired
	private AdminService adminService;
	Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@PutMapping("/{ticketGenerate}/status")
	@Operation(summary = "Admin Update The Ticket Status (Id)", description = "Admin Update The Ticket Status Based On The Id")
	@PreAuthorize("hasRole('ADMIN')") // Restrict to admins
	public ResponseEntity<Ticket> updateTicketStatus(@PathVariable String ticketGenerate, @RequestBody Ticket ticket, @RequestParam String inProgressis0Completedis1){
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			Ticket updatedResponse = adminService.updateTicketStatus(ticketGenerate, ticket, inProgressis0Completedis1);
			return ResponseEntity.ok(updatedResponse);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/get_alltickets")
	@Operation(summary = "Admin Get All Tickets", description = " Admin Get All the Created Tickets")
	public List<Ticket> getAllTickets() {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			return adminService.getAllTickets();
		} else {

			return (List<Ticket>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	@GetMapping("/tickets/{ticketGenerate}")
	@Operation(summary = "Admin Get the Tickets(Id)", description = " Admin Get the Tickets Based On The Particular Id")
	public ResponseEntity<Ticket> getTicketsByUsername(@RequestParam String ticketGenerate) {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			return ResponseEntity.ok(adminService.getTicketByTicketGenerate(ticketGenerate));
		} else {
 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
 
	}

	@GetMapping("/tickets/pending")
	@Operation(summary = "Admin Get Pending Tickets", description = " Admin Get All The Pending Tickets")
	public ResponseEntity<List<Ticket>> getAllPendingTickets() {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			return ResponseEntity.ok(adminService.getAllPendingTickets());
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	@GetMapping("/tickets/in progress")
	@Operation(summary = "Admin Get In Progress Tickets", description = " Admin Get All The In Progress Tickets")
	public ResponseEntity<List<Ticket>> getAllInProgressTickets() {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			return ResponseEntity.ok(adminService.getAllInProgressTickets());
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/tickets/completed")
	@Operation(summary = "Admin Get Completed Tickets", description = " Admin Get All The Completed Tickets")
	public ResponseEntity<List<Ticket>> getAllCompletedTickets() {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			return ResponseEntity.ok(adminService.getAllCompletedTickets());
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/tickets/cancelled")
	@Operation(summary = "Admin Get Cancelled Tickets", description = " Admin Get All The Cancelled Tickets")
	public ResponseEntity<List<Ticket>> getAllCancelledTickets() {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("ADMIN".equalsIgnoreCase(loginDto.getRole())) {
			return ResponseEntity.ok(adminService.getAllCancelledTickets());
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
