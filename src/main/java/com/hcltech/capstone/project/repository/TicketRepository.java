package com.hcltech.capstone.project.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcltech.capstone.project.entity.Ticket;
import com.hcltech.capstone.project.entity.TicketStatus;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	List<Ticket> findByStatus(TicketStatus status);

	Optional<Ticket> findByTicketGenerate(String ticketGenerate);

	@Query("SELECT t FROM Ticket t WHERE t.status = :status AND t.createdDate < :cutoffTime")
	List<Ticket> findTicketsForAutoCancel(@Param("cutoffTime") LocalDateTime cutoffTime,
			@Param("status") TicketStatus status);

	Optional<Ticket> findByIdAndStatus(Long id, TicketStatus status);
}