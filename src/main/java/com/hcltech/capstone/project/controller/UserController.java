package com.hcltech.capstone.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "Endpoints for managing User-related operations")
public class UserController {
	@Autowired
	private UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/Create_tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "User to Create the Tickets", description = "User to Create the new Tickets")
	public ResponseEntity<Ticket> raiseTicket(@RequestBody Ticket ticket) {
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("USER".equalsIgnoreCase(loginDto.getRole())) {
			return ResponseEntity.ok(userService.raiseTicket(ticket));
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}
	
	@GetMapping("/ticketGenerate")
	@Operation(summary = "User Get Ticket by ticketGenerate", description = "User Get Ticket by ticketGenerate")
	public ResponseEntity<Ticket> getTicketByTicketGenerate(@RequestParam String ticketGenerate) {
		Ticket ticket =userService.getTicketByTicketGenerate(ticketGenerate);
		if (ticket !=null) {
			return ResponseEntity.ok(ticket);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/getAllticketByUsername")
	@Operation(summary = "User getAllTicket by username", description = "User getAllTicket by username")
	public ResponseEntity<List<Ticket>> getTicketByUsername() {
		List<Ticket> username= userService.getTicketByUsername();
		if(username!=null)
		{
			return ResponseEntity.ok(username);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	@PutMapping("/{ticketCancelledByUser}/status")
	@Operation(summary = "User Cancelled The Ticket(ticketGenerete)", description = "User Cancelled The Ticket(ticketGenerete)")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Ticket> updateUserCancelledStatus(@RequestParam String ticketGenerate, @RequestParam String reason)
	{
		LoginDto loginDto = UserContextHolder.getLoginDto();
		if ("USER".equalsIgnoreCase(loginDto.getRole())) {
			Ticket updatedResponse = userService.updateUserCancelledStatus(ticketGenerate, reason);
			return ResponseEntity.ok(updatedResponse);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
 

}
