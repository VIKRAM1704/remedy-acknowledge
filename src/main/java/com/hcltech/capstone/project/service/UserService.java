package com.hcltech.capstone.project.service;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.hcltech.capstone.project.config.UserContextHolder;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Role;
import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;
import com.hcltech.capstone.project.exception.ResourceNotFoundException;
import com.hcltech.capstone.project.exception.UserNotFoundException;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.repository.TicketRepository;


@Service
public class UserService
{
	
	Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
    private LoginRepository loginRepository;
 
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
	private PasswordEncoder passwordEncoder;
    
    
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
     
    @SuppressWarnings("deprecation")
	public Ticket raiseTicket(Ticket ticket) {
    	LoginDto loginDto = UserContextHolder.getLoginDto();
    	 String ticketId = String.format("TICKET%d%s%d%05d",
    		        LocalDate.now().getDayOfMonth(),
    		        LocalDate.now().getMonth(),
    		        LocalDate.now().getYear(),
    		        SECURE_RANDOM.nextInt(100000)
    		    );
    			if(loginDto.isActive())
    			{
    				Login user=new Login();
    				user.setId(loginDto.getId());
    				user.setUsername(loginDto.getUsername());
    				user.setPassword(loginDto.getPassword());
    				user.setActive(loginDto.isActive());
    				user.setRole(Role.USER);
    				
	    		    ticket.setTicketGenerate(ticketId);
	    		    ticket.setUser(user);
	    		  
	    		    ticketRepository.save(ticket);
	    		    
    			}
    			else
    			{
    				ticket.setTicketGenerate(null);
	    		    ticket.setUser(null);
    			}
 
    		return ticket;
 
    }
   
    @Scheduled(fixedRate = 60000)
    public void autoCancelTickets() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(1); // Change days/hours as needed
        List<Ticket> ticketsToCancel = ticketRepository.findTicketsForAutoCancel(cutoffTime, TicketStatus.PENDING);
 
        for (Ticket ticket : ticketsToCancel) {
            ticket.setStatus(TicketStatus.CANCELLED);
            ticketRepository.save(ticket);
        }
    }
    

    
    public Ticket getTicketByTicketGenerate(@RequestParam String ticketGenerate) {
 	   LoginDto loginDto = UserContextHolder.getLoginDto();
 	   List<Ticket> username=loginRepository.findByUsername(loginDto.getUsername())
 			   .map(user -> user.getTickets())
 			   .orElseThrow(()-> new UserNotFoundException("user not found: " +loginDto.getUsername()));
 		if (loginDto.isActive())
 		{
 	    	Optional<Ticket> ticket= ticketRepository.findByTicketGenerate(ticketGenerate);
 			if (ticket.isPresent() && username.contains(ticket.get())) {
 				return ticket.get();
 			}
 			throw new ResourceNotFoundException("Ticket is not found in this "+loginDto.getUsername()+" Username.");
  
 		}
 		throw new UserNotFoundException("Ticket is not Authenticated.");
 	}
    
    
    public List<Ticket> getTicketByUsername() {
        LoginDto loginDto = UserContextHolder.getLoginDto();
     
        if (loginDto.isActive()) {
            return loginRepository.findByUsername(loginDto.getUsername())
                    .map(Login::getTickets) // Extract tickets safely if login exists
                    .orElseThrow(() -> new UserNotFoundException("User not found: " + loginDto.getUsername()));
        }
        
        return null;
    }
    
    public Ticket updateUserCancelledStatus(String ticketGenerate, String updatedTicket)
	{
		Ticket existingTicket = ticketRepository.findByTicketGenerate(ticketGenerate)
	     .orElseThrow(() -> new RuntimeException("Ticket not found"));
		existingTicket.setStatus(TicketStatus.CANCELLED);	
		existingTicket.setComment(updatedTicket);
		return ticketRepository.save(existingTicket);
		}
}
