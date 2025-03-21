package com.hcltech.capstone.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.exception.UserNotFoundException;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.util.Constant;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	private final LoginRepository loginRepository;

	@Autowired
	public JWTUserDetailsService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
		Login login = null;
		try {
			login = getLogin(username);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return new org.springframework.security.core.userdetails.User(login.getUsername(), login.getPassword(),
				new ArrayList<>());
	}

	public Login getLogin(String username) {
		Optional<Login> users = loginRepository.findByUsername(username);
		if (users.isEmpty()) {
			throw new UserNotFoundException(Constant.INVALID_CREDENTIALS);
		}
		return users.get();
	}
}
