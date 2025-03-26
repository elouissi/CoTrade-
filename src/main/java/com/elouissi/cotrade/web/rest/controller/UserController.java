package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.service.DTO.UserDTO;
import com.elouissi.cotrade.service.DTO.PasswordChangeDTO;
import com.elouissi.cotrade.service.UserService;
import com.elouissi.cotrade.web.rest.VM.UserVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/V1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserVM>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile() {
        UserDTO userDTO = userService.getCurrentUserProfile();
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUserProfile(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserProfile(userDTO);
        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping("/toTrader/{userId}")
    public ResponseEntity<Map<String, String>> updateRole(@PathVariable UUID userId) {
        userService.toTrader(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "l'user est changé");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HashMap<String, String>> changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(
                passwordChangeDTO.getCurrentPassword(),
                passwordChangeDTO.getNewPassword()
        );

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Password changed successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HashMap<String, String>> deleteAccount() {
        userService.deleteCurrentUserAccount();

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Account deleted successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id){

    return ResponseEntity.ok(userService.getUserById(id).get());

    }

}
