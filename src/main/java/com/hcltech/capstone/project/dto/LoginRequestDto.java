package com.hcltech.capstone.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
	@Schema(description = "Username of the user", example = "squad_6")
	@NotBlank(message = "UserName required")
	private String userName;

	@Schema(description = "Password of the user", example = "P@ssw0rd")
	@NotBlank(message = "Password required")
	private String password;

	public LoginRequestDto(@NotBlank(message = "UserName required") String userName,
			@NotBlank(message = "Password required") String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
