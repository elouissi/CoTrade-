package com.elouissi.cotrade.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private UUID id;
    private String body;
    private String attachment;
    private boolean isRead;
    private LocalDateTime time;
    private UUID senderId;
    private UUID receiverId;
    private UUID conversationId;
    private boolean isSentByCurrentUser;
}

