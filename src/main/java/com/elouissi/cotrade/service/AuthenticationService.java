package com.elouissi.cotrade.service;

import com.elouissi.cotrade.config.JwtService;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.enums.Role;
import com.elouissi.cotrade.repository.UserRepository;
import com.elouissi.cotrade.web.rest.VM.AuthenticateRequest;
import com.elouissi.cotrade.web.rest.VM.AuthenticationResponse;
import com.elouissi.cotrade.web.rest.VM.RegisterVM;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse register(RegisterVM request) {
        var user = AppUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .location(request.getLocation())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.VISITOR)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


}
