package com.elouissi.cotrade.domain;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne
    private AppUser user;
}