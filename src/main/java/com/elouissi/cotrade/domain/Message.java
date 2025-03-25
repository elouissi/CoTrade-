package com.elouissi.cotrade.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String body;
    private String attachment;
    private boolean isRead;
    private UUID senderId;
    private UUID receiverId;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;



    private LocalDateTime time = LocalDateTime.now();

}