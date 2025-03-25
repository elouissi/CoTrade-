package com.elouissi.cotrade.service;

import com.elouissi.cotrade.config.security.SecurityUtils;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.enums.Role;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.repository.UserRepository;
import com.elouissi.cotrade.service.DTO.UserDTO;
import com.elouissi.cotrade.web.rest.VM.UserVM;
import com.elouissi.cotrade.web.rest.VM.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
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
    public List<UserVM> getAll(){
       return userRepository.findAll()
               .stream().map(userMapper::toVM)
               .collect(Collectors.toList());
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
    public void toTrader(UUID userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("AppUser not found"));

        user.setRole(Role.TRADER);
        userRepository.save(user);

    }

    public void deleteCurrentUserAccount() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new IllegalStateException("Current user login not found"));

        AppUser user = userRepository.findByEmail(currentUserLogin)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + currentUserLogin));

        postRepository.deleteAllByUser(user);
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
