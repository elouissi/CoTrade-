package com.elouissi.cotrade.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String filePath;

    @ManyToOne
    private Post post;
}
