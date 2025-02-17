package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.service.PhotoService;
import com.elouissi.cotrade.web.rest.VM.PhotoVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PhotoVM> createPhoto(@Valid @RequestBody PhotoVM photoVM) {
        PhotoVM createdPhoto = photoService.createPhoto(photoVM);
        return ResponseEntity.ok(createdPhoto);
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PhotoVM>> createMultiplePhotos(@Valid @RequestBody List<PhotoVM> photoVMs) {
        List<PhotoVM> createdPhotos = photoService.createMultiplePhotos(photoVMs);
        return ResponseEntity.ok(createdPhotos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoVM> getPhoto(@PathVariable UUID id) {
        PhotoVM photo = photoService.getPhoto(id);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PhotoVM>> getPhotosByPostId(@PathVariable UUID postId) {
        List<PhotoVM> photos = photoService.getPhotosByPostId(postId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping
    public ResponseEntity<List<PhotoVM>> getAllPhotos() {
        List<PhotoVM> photos = photoService.getAllPhotos();
        return ResponseEntity.ok(photos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PhotoVM> updatePhoto(
            @PathVariable UUID id,
            @Valid @RequestBody PhotoVM photoVM
    ) {
        PhotoVM updatedPhoto = photoService.updatePhoto(id, photoVM);
        return ResponseEntity.ok(updatedPhoto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePhoto(@PathVariable UUID id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePhotosByPostId(@PathVariable UUID postId) {
        photoService.deletePhotosByPostId(postId);
        return ResponseEntity.noContent().build();
    }
}