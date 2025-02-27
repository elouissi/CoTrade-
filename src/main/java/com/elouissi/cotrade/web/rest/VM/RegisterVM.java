package com.elouissi.cotrade.web.rest.VM;

import com.elouissi.cotrade.domain.enums.Role;
import com.elouissi.cotrade.validation.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterVM {

    @NotBlank(message = "Le nom est requis")
    private String name;


    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial")
    private String password;




    @UniqueEmail(message = "Email est deja existe")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "location is required")
    private String location;


    private Role role= Role.VISITOR;
}