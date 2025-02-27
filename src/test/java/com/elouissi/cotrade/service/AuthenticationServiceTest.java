package com.elouissi.cotrade.service;

import com.elouissi.cotrade.config.JwtService;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.enums.Role;
import com.elouissi.cotrade.repository.UserRepository;
import com.elouissi.cotrade.web.rest.VM.AuthenticateRequest;
import com.elouissi.cotrade.web.rest.VM.RegisterVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterVM registerRequest;
    private AuthenticateRequest authRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerRequest = RegisterVM.builder()
                .name("John")
                .email("john@test.com")
                .password("password")
                .location("Paris")
                .build();

        authRequest = new AuthenticateRequest("john@test.com", "password");
    }

    @Test
    void register_ShouldCreateUserWithEncodedPassword() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(AppUser.class))).thenReturn("jwtToken");

        var response = authenticationService.register(registerRequest);

        assertNotNull(response.getToken());
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    void authenticate_ShouldReturnToken() {
        AppUser mockUser = AppUser.builder().email("john@test.com").build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(any(AppUser.class))).thenReturn("jwtToken");

        var response = authenticationService.authenticate(authRequest);

        assertNotNull(response.getToken());
        verify(authenticationManager).authenticate(any());
    }
}