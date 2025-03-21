package com.hcltech.capstone.project.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.dto.LoginRequestDto;
import com.hcltech.capstone.project.dto.RegistrationDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Role;
import com.hcltech.capstone.project.exception.UserAlreadyExistsException;
import com.hcltech.capstone.project.exception.UserNotFoundException;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.util.Constant;
import com.hcltech.capstone.project.util.JWTUtils;

@Service
public class AuthService {

	private final LoginRepository loginRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder bCryptPasswordEncoder;
	private final ModelMapper modelMapper;
	private final JWTUtils jwtUtils;

	@Autowired
	public AuthService(LoginRepository loginRepository, AuthenticationManager authenticationManager,
			PasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper, JWTUtils jwtUtils) {
		this.loginRepository = loginRepository;
		this.authenticationManager = authenticationManager;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.modelMapper = modelMapper;
		this.jwtUtils = jwtUtils;
	}

	public String registerUser(RegistrationDto registrationDto) {
		Optional<Login> login = loginRepository.findByUsername(registrationDto.getUsername());
		if (login.isEmpty()) {
			Login newUser = new Login();
			newUser.setUsername(registrationDto.getUsername());
			newUser.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
			newUser.setActive(true);
			newUser.setRole(Role.valueOf(registrationDto.getRole()));
			loginRepository.save(newUser);
			return Constant.REGISTRATION_SUCCESS_FULL;
		} else{
			return "UserName Already Exists,Please try with different UserName";
		
		}
		
	}

	public LoginDto login(LoginRequestDto loginRequestDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if (authentication.isAuthenticated()) {
			Login login = loginRepository.findByUsername(loginRequestDto.getUserName())
					.orElseThrow(() -> new RuntimeException(Constant.USER_NAME_NOT_FOUND));
			LoginDto loginDTO = modelMapper.map(login, LoginDto.class);
			loginDTO.setToken(jwtUtils.generateToken(login));
			return loginDTO;
		} else {
			throw new UserNotFoundException("Invalid credentials");
		}
	}
}