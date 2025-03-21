package com.hcltech.capstone.project.dto;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LoginDto {
	private int id;

	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 8, message = "Username must be between 3 and 8 characters")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 4, message = "Password must be at least 4 characters")
	private String password;

	@NotBlank(message = "Role is required")
	@Pattern(regexp = "ADMIN|USER", message = "Role must be either ADMIN and USER")
	private String role;

	private String token;

	@Column(name = "is_Active")
	private boolean isActive;

	@CreatedDate
	@Column(name = "created_At")
	private LocalDate createdAt;

	@LastModifiedDate
	@Column(name = "updated_At")
	private LocalDate updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

}
