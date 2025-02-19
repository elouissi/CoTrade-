package com.elouissi.cotrade.service.DTO;

import com.elouissi.cotrade.domain.enums.PostStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PhotoDTO {
    private UUID id;
    private String title;
    private String filePath;
}
