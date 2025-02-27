package com.elouissi.cotrade.service.DTO;

import java.util.UUID;
import lombok.Data;

@Data
public class MessageDTO {
    private UUID id;
    private String body;
    private String attachment;
    private boolean isRead;
    private UUID senderId;
    private UUID receiverId;
}