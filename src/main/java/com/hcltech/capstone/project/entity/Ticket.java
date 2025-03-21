package com.hcltech.capstone.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 100)
	private String title;

	@NotNull
	@Size(min = 1, max = 500)
	private String description;

	@NotNull
	private LocalDateTime createdDate;

	@NotNull
	@Size(min = 1, max = 50)
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Login user;

	@ManyToOne
	@JoinColumn(name = "updated_by_admin_id")
	private Login UpdatedByAdmin;

	private String comment;
	private String acknowledgment;

	@NotNull
	private LocalDateTime completedDate;

	private String ticketGenerate;

	@PrePersist
	public void prePersist() {
		if (this.status == null) {
			this.status = TicketStatus.PENDING;
		}
		this.createdDate = LocalDateTime.now(); // Optional: Set created date }
	}

	public Ticket() {

	}

	public Ticket(Long id, @NotNull @Size(min = 1, max = 100) String title,
			@NotNull @Size(min = 1, max = 500) String description, @NotNull LocalDateTime createdDate,
			@NotNull @Size(min = 1, max = 50) TicketStatus status, Login user, Login updatedByAdmin, String comment,
			String acknowledgment, @NotNull LocalDateTime completedDate, String ticketGenerate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.status = status;
		this.user = user;
		UpdatedByAdmin = updatedByAdmin;
		this.comment = comment;
		this.acknowledgment = acknowledgment;
		this.completedDate = completedDate;
		this.ticketGenerate = ticketGenerate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Login getUser() {
		return user;
	}

	public void setUser(Login user) {
		this.user = user;
	}

	public Login getUpdatedByAdmin() {
		return UpdatedByAdmin;
	}

	public void setUpdatedByAdmin(Login updatedByAdmin) {
		UpdatedByAdmin = updatedByAdmin;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAcknowledgment() {
		return acknowledgment;
	}

	public void setAcknowledgment(String acknowledgment) {
		this.acknowledgment = acknowledgment;
	}

	public LocalDateTime getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(LocalDateTime completedDate) {
		this.completedDate = completedDate;
	}

	public String getTicketGenerate() {
		return ticketGenerate;
	}

	public void setTicketGenerate(String ticketGenerate) {
		this.ticketGenerate = ticketGenerate;
	}
}
