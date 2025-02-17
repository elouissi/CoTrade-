package com.elouissi.cotrade.service.DTO;

import com.elouissi.cotrade.domain.enums.PostStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostDTO {
    private UUID id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
    private String title;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotBlank(message = "La catégorie est obligatoire")
    private String category;

    @NotBlank(message = "La localisation est obligatoire")
    private String location;

    @NotNull(message = "Le statut du post est obligatoire")
    private PostStatus status;

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private UUID userId;

    private List<PhotoDTO> photos;  // Changed from List<UUID> to List<PhotoDTO>
}
