package com.elouissi.cotrade.web.rest.controller.auth;

import com.elouissi.cotrade.service.AuthenticationService;
import com.elouissi.cotrade.web.rest.VM.AuthenticateRequest;
import com.elouissi.cotrade.web.rest.VM.AuthenticationResponse;
import com.elouissi.cotrade.web.rest.VM.RegisterVM;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/V1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final RestTemplate restTemplate;



    @GetMapping("/read")
    public String read(){
        return "hello";
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterVM request
    ){
        return ResponseEntity.ok(service.register(request));

    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
