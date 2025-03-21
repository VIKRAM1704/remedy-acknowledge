package com.hcltech.capstone.project.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcltech.capstone.project.dto.DecodeTokenDto;
import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.util.Constant;
import com.hcltech.capstone.project.util.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	private final JWTUtils jwtUtils;
	private final LoginRepository loginRepository;

	public JWTRequestFilter(JWTUtils jwtTokenUtil, LoginRepository loginRepository) {
		this.jwtUtils = jwtTokenUtil;
		this.loginRepository = loginRepository;
	}

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
			@NotNull FilterChain filterChain) throws IOException {
		try {
			if (shouldSkipFilter(request)) {
				filterChain.doFilter(request, response);
				return;
			}
			String jwtToken = extractJwtToken(request.getHeader(Constant.AUTHORIZATION));
			if (jwtToken != null) {
				processJwtToken(jwtToken, request);
			} else {
				throw new RuntimeException("JWT Token does not begin with Bearer String");
			}
			filterChain.doFilter(request, response);
		} catch (RuntimeException servletException) {
			handleException(response, new RuntimeException(servletException.getMessage()));
		} catch (Exception exception) {
			handleException(response, new RuntimeException("Invalid Authorization"));
		}
	}

	private void handleException(HttpServletResponse response, RuntimeException exception) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"errorCode\": \"401\", \"message\": \"" + exception.getMessage() + "\"}");
	}

	private boolean shouldSkipFilter(HttpServletRequest request) {
		return StringUtils.isBlank(request.getHeader(Constant.AUTHORIZATION));
	}

	String extractJwtToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith(Constant.BEARER)) {
			return authorizationHeader.replace(Constant.BEARER, "");
		}
		return null;
	}

	void processJwtToken(String jwtToken, HttpServletRequest request) throws ServletException {
		try {
			String userName = getUserNameFromJwtToken(jwtToken);
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				Login login = getUser(userName);
				if (login != null) {
					validatedTokenAndUserDetails(jwtToken, request, userName, login);
				}
			}
		} catch (ExpiredJwtException e) {
			logger.warn(Constant.ACCESS_TOKEN_HAS_EXPIRED + e.getMessage());
			throw new ServletException(Constant.ACCESS_TOKEN_HAS_EXPIRED);
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
	}

	private void validatedTokenAndUserDetails(String jwtToken, HttpServletRequest request, String userName, Login login)
			throws ServletException {
		try {
			UserDetails userDetails = createUserDetails(userName, login.getPassword());
			if (Boolean.TRUE.equals(jwtUtils.validateToken(jwtToken, userDetails))) {
				authenticateWithJwtToken(userDetails, request);
			}
		} catch (Exception e) {
			throw new ServletException(Constant.ACCESS_TOKEN_IS_INVALID);
		}
	}

	public Login getUser(String userName) {
		Optional<Login> Login = loginRepository.findByUsername(userName);
		if (Login.isEmpty()) {
			throw new RuntimeException(Constant.INVALID_CREDENTIALS);
		}
		return Login.get();
	}

	private String getUserNameFromJwtToken(String jwtToken) throws JsonProcessingException {
		DecodeTokenDto dto = extractTokenDto(jwtToken);
		if (dto.getSub() != null) {
			Optional<Login> user = loginRepository.findByUsername(dto.getSub());
			user.ifPresent(u -> {
				LoginDto loginDto = new LoginDto();
				loginDto.setId(u.getId());
				loginDto.setUsername(u.getUsername());
				loginDto.setPassword(u.getPassword());
				loginDto.setRole(String.valueOf(user.get().getRole()));
				loginDto.setActive(u.isActive());
				UserContextHolder.setLoginDto(loginDto);
			});
			return dto.getSub();
		}
		return null;
	}

	private DecodeTokenDto extractTokenDto(String jwtToken) throws JsonProcessingException {
		String[] split = jwtToken.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(split[1]));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		return objectMapper.readValue(payload, DecodeTokenDto.class);
	}

	private UserDetails createUserDetails(String username, String password) {
		return new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
	}

	void authenticateWithJwtToken(UserDetails userDetails, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = jwtUtils.getAuthentication(userDetails);
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

}
