package com.hcltech.capstone.project.config;

import com.hcltech.capstone.project.dto.LoginDto;

public class UserContextHolder {

	private UserContextHolder() {
	}

	private static final ThreadLocal<LoginDto> USER_CONTEXT = new ThreadLocal<>();

	public static void setLoginDto(LoginDto loginContextDTO) {
		USER_CONTEXT.set(loginContextDTO);
	}

	public static LoginDto getLoginDto() {
		return USER_CONTEXT.get();
	}

	public static void clear() {
		USER_CONTEXT.remove();
	}
}