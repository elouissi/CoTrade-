package com.elouissi.cotrade.domain;

import com.elouissi.cotrade.domain.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private String category;
    private String location;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "post")
    private List<Photo> photos;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations = new ArrayList<>();
}