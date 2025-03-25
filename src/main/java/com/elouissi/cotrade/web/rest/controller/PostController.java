package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.enums.PostStatus;
import com.elouissi.cotrade.service.DTO.PostDTO;
import com.elouissi.cotrade.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/V1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<PostDTO> createPost(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("location") String location,
            @RequestParam("status") String status,
            @RequestParam("userId") String userId,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
    ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setDescription(description);
        postDTO.setCategory(category);
        postDTO.setLocation(location);
        postDTO.setStatus(PostStatus.valueOf(status.toUpperCase()));
        postDTO.setUserId(UUID.fromString(userId));
        System.out.println(postDTO);


        PostDTO createdPost = postService.createPost(postDTO, photos);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable UUID id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("location") String location,
            @RequestParam("status") String status,
            @RequestParam("userId") String userId,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
    ) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setDescription(description);
        postDTO.setCategory(category);
        postDTO.setLocation(location);
        postDTO.setStatus(PostStatus.valueOf(status));
        postDTO.setUserId(UUID.fromString(userId));

        PostDTO updatedPost = postService.updatePost(id, postDTO, photos);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable UUID id) {
        PostDTO post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }



    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/created/{id}")
    public ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable UUID id) {
        List<PostDTO> posts = postService.getAllPostsByCreated(id);
        return ResponseEntity.ok(posts);
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('TRADER')")
//    public ResponseEntity<PostDTO> updatePost(
//            @PathVariable UUID id,
//            @Valid @RequestBody PostDTO postDTO
//    ) {
//        PostDTO updatedPost = postService.updatePost(id, postDTO);
//        return ResponseEntity.ok(updatedPost);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TRADER')")
    public HashMap<String, String> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        HashMap<String,String> message = new HashMap<>();
        message.put("message","succses");
        return message;
    }
}