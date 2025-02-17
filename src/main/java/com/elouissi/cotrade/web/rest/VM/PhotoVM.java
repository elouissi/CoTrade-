package com.elouissi.cotrade.web.rest.VM;

import com.elouissi.cotrade.domain.enums.PostStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PhotoVM {
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String filePath;

    @NotNull
    private UUID postId;
}