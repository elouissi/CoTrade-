package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.repository.PhotoRepository;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.service.DTO.PostDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.PostMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final PostMapper postMapper;

    public PostDTO createPost(PostDTO postDTO, List<MultipartFile> photoFiles) {
        Post post = postMapper.toEntity(postDTO);
        Post savedPost = postRepository.save(post);

        if (photoFiles != null && !photoFiles.isEmpty()) {
            List<Photo> photos = processPhotoFiles(photoFiles, savedPost);
            savedPost.setPhotos(photos);
        }

        return postMapper.toDto(savedPost);
    }

    public PostDTO updatePost(UUID id, PostDTO postDTO, List<MultipartFile> photoFiles) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

        Post updatedPost = postMapper.toEntity(postDTO);
        updatedPost.setId(id);

        if (photoFiles != null) {
            photoRepository.deleteByPostId(id);
            List<Photo> newPhotos = processPhotoFiles(photoFiles, updatedPost);
            updatedPost.setPhotos(newPhotos);
        }

        Post savedPost = postRepository.save(updatedPost);
        return postMapper.toDto(savedPost);
    }

    private List<Photo> processPhotoFiles(List<MultipartFile> photoFiles, Post post) {
        return photoFiles.stream()
                .map(file -> {
                    try {
                        // Générer un nom de fichier unique
                        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                        // Chemin où sauvegarder le fichier
                        String uploadDir = "static/uploads/";
                        Path uploadPath = Paths.get(uploadDir);

                        if (!Files.exists(uploadPath)) {
                            Files.createDirectories(uploadPath);
                        }

                        // Sauvegarder le fichier
                        Path filePath = uploadPath.resolve(fileName);
                        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        // Créer et sauvegarder l'entité Photo
                        Photo photo = new Photo();
                        photo.setFilePath("uploads/" + fileName);
                        photo.setTitle(fileName);
                        photo.setPost(post);
                        return photoRepository.save(photo);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
                    }
                })
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public PostDTO getPost(UUID id) {
        return postRepository.findById(id)
                .map(postMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

//    public PostDTO updatePost(UUID id, PostDTO postDTO) {
//        Post existingPost = postRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
//
//        Post updatedPost = postMapper.toEntity(postDTO);
//        updatedPost.setId(id);
//
//        if (postDTO.getPhotos() != null) {
//            photoRepository.deleteByPostId(id);
//
//            List<Photo> newPhotos = postDTO.getPhotos().stream()
//                    .map(photoDTO -> {
//                        Photo photo = postMapper.photoDTOToPhoto(photoDTO);
//                        photo.setPost(updatedPost);
//                        return photo;
//                    })
//                    .collect(Collectors.toList());
//
//            photoRepository.saveAll(newPhotos);
//            updatedPost.setPhotos(newPhotos);
//        }
//
//        Post savedPost = postRepository.save(updatedPost);
//        return postMapper.toDto(savedPost);
//    }

    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }

        // Supprimer d'abord les photos associées
        photoRepository.deleteByPostId(id);

        // Maintenant, supprimer le post
        postRepository.deleteById(id);
    }

}