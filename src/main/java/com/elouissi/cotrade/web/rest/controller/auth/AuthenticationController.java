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
//    @PostMapping("/keycloak-login")
//    public ResponseEntity<?> keycloakLogin(@RequestBody KeyclockRequest request) {
//        try {
//            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//            formData.add("grant_type", "password");
//            formData.add("client_id", "hunters_league");
//            formData.add("client_secret", "jx9tJVq3ZwbkGjR9Y0kAMRURkIc69PZF");
//            formData.add("username", request.getUsername());
//            formData.add("password", request.getPassword());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
//
//            ResponseEntity<Map> response = restTemplate.postForEntity(
//                    "http://localhost:8080/realms/hunters_league/protocol/openid-connect/token",
//                    entity,
//                    Map.class
//            );
//
//            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//                return ResponseEntity.ok(response.getBody());
//            }
//
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        } catch (RestClientException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Authentication error: " + e.getMessage());
//        }
//    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
