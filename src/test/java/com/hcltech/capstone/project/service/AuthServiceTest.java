package com.hcltech.capstone.project.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;

import com.hcltech.capstone.project.dto.LoginDto;
import com.hcltech.capstone.project.dto.LoginRequestDto;
import com.hcltech.capstone.project.dto.RegistrationDto;
import com.hcltech.capstone.project.entity.Login;
import com.hcltech.capstone.project.entity.Role;
import com.hcltech.capstone.project.repository.LoginRepository;
import com.hcltech.capstone.project.service.AuthService;
import com.hcltech.capstone.project.util.Constant;
import com.hcltech.capstone.project.util.JWTUtils;
class AuthServiceTest {
    @InjectMocks
 
    private AuthService authService;
    @Mock
 
    private LoginRepository loginRepository;
    @Mock
 
    private AuthenticationManager authenticationManager;
    @Mock
 
    private PasswordEncoder passwordEncoder;
    @Mock
 
    private ModelMapper modelMapper;
    @Mock
 
    private JWTUtils jwtUtils;
    @Mock
 
    private Authentication authentication;
    @BeforeEach
 
    void setUp() {
 
        MockitoAnnotations.openMocks(this);
 
    }
    @Test
 
    void testRegisterUser_Success() {
 
        RegistrationDto registrationDto = new RegistrationDto("testUser", "password", "USER");
 
        Login login = new Login();
 
        login.setUsername("testUser");
 
        login.setPassword("encodedPassword");
 
        login.setRole(Role.USER);
        when(loginRepository.findByUsername("testUser")).thenReturn(Optional.empty());
 
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
 
        when(loginRepository.save(any(Login.class))).thenReturn(login);
        String result = authService.registerUser(registrationDto);
 
        assertEquals(Constant.REGISTRATION_SUCCESS_FULL, result);
 
    }
    @Test
 
    void testRegisterUser_UserAlreadyExists() {
 
        RegistrationDto registrationDto = new RegistrationDto("testUser", "password", "USER");
 
        Login login = new Login();
 
        when(loginRepository.findByUsername("testUser")).thenReturn(Optional.of(login));
        String result = authService.registerUser(registrationDto);
 
        assertEquals("UserName Already Exists,Please try with different UserName", result);
 
    }
    @Test
 
    void testLogin_Success() {
 
        LoginRequestDto loginRequestDto = new LoginRequestDto("testUser", "password");
 
        Login login = new Login();
 
        login.setUsername("testUser");
 
        login.setPassword("encodedPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
 
        when(authentication.isAuthenticated()).thenReturn(true);
 
        when(loginRepository.findByUsername("testUser")).thenReturn(Optional.of(login));
 
        when(modelMapper.map(login, LoginDto.class)).thenReturn(new LoginDto());
 
        when(jwtUtils.generateToken(login)).thenReturn("jwtToken");
        LoginDto result = authService.login(loginRequestDto);
 
        assertNotNull(result);
 
        assertEquals("jwtToken", result.getToken());
 
    }
    @Test
 
    void testLogin_InvalidCredentials() {
 
        LoginRequestDto loginRequestDto = new LoginRequestDto("testUser", "password");
 
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
 
        when(authentication.isAuthenticated()).thenReturn(false);
        assertThrows(RuntimeException.class, () -> authService.login(loginRequestDto));
 
    }
 
}
 