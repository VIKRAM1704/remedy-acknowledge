package com.hcltech.capstone.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationDto {

	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 8, message = "Username must be between 3 and 8 characters")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 4, message = "Password must be at least 4 characters")
	private String password;

	@NotBlank(message = "Role is required")
	@Pattern(regexp = "ADMIN|USER", message = "Role must be either ADMIN and USER")
	private String role;

	public RegistrationDto() {
		super();
	}

	public RegistrationDto(
			@NotBlank(message = "Username is required") @Size(min = 3, max = 8, message = "Username must be between 3 and 8 characters") String username,
			@NotBlank(message = "Password is required") @Size(min = 4, message = "Password must be at least 4 characters") String password,
			@NotBlank(message = "Role is required") @Pattern(regexp = "ADMIN|USER", message = "Role must be either ADMIN and USER") String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
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
}
