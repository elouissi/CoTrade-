package com.elouissi.cotrade.service;

import com.elouissi.cotrade.config.security.SecurityUtils;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.repository.UserRepository;
import com.elouissi.cotrade.service.DTO.UserDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO getCurrentUserProfile() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new IllegalStateException("Current user login not found"));

        AppUser user = userRepository.findByEmail(currentUserLogin)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + currentUserLogin));

        return userMapper.EntityToDto(user);
    }

    public UserDTO updateUserProfile(UserDTO userDTO) {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new IllegalStateException("Current user login not found"));

        AppUser user = userRepository.findByEmail(currentUserLogin)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + currentUserLogin));

        user.setName(userDTO.getName());
        user.setLocation(userDTO.getLocation());

        if (!user.getEmail().equals(userDTO.getEmail()) &&
                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        user.setEmail(userDTO.getEmail());

        AppUser savedUser = userRepository.save(user);
        return userMapper.EntityToDto(savedUser);
    }

    public void changePassword(String currentPassword, String newPassword) {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new IllegalStateException("Current user login not found"));

        AppUser user = userRepository.findByEmail(currentUserLogin)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + currentUserLogin));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
    /**
     * Supprime le compte de l'utilisateur actuellement connecté.
     * Cette opération est irréversible.
     *
     * @throws IllegalStateException si aucun utilisateur n'est connecté
     * @throws EntityNotFoundException si l'utilisateur connecté n'existe pas dans la base de données
     */

    public void deleteCurrentUserAccount() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new IllegalStateException("Current user login not found"));

        AppUser user = userRepository.findByEmail(currentUserLogin)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + currentUserLogin));

        userRepository.delete(user);
    }
    public Optional<UserDTO> getUserById(UUID id){
        Optional<AppUser> optionalAppUser = userRepository.findById(id);
        if (optionalAppUser.isPresent()){
            return Optional.ofNullable(userMapper.EntityToDto(userRepository.findById(id).get()));
        }else {
            throw new RuntimeException("le user ne trouve pas");
        }
    }
}
