package com.elouissi.cotrade.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private UUID id;
    private UUID senderId;
    private UUID receiverId;
    private UUID postId;

    // Informations supplémentaires
    private String senderName;
    private String receiverName;
    private String postTitle;
    private String postImage;

    // Dernier message
    private String lastMessage;
    private LocalDateTime updatedAt;

    // Compteur de messages non lus
    private int unreadCount;
}

