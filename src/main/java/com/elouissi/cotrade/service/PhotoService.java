package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.repository.PhotoRepository;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.web.rest.VM.PhotoVM;
import com.elouissi.cotrade.web.rest.VM.mapper.PhotoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final PostRepository postRepository;
    private final PhotoMapper photoMapper;

    public PhotoVM createPhoto(PhotoVM photoVM) {
        Post post = postRepository.findById(photoVM.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + photoVM.getPostId()));

        Photo photo = photoMapper.toEntity(photoVM);
        photo.setPost(post);
        Photo savedPhoto = photoRepository.save(photo);
        return photoMapper.toVM(savedPhoto);
    }

    public List<PhotoVM> createMultiplePhotos(List<PhotoVM> photoVMs) {
        return photoVMs.stream()
                .map(this::createPhoto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PhotoVM getPhoto(UUID id) {
        return photoRepository.findById(id)
                .map(photoMapper::toVM)
                .orElseThrow(() -> new EntityNotFoundException("Photo not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PhotoVM> getPhotosByPostId(UUID postId) {
        return photoRepository.findByPostId(postId).stream()
                .map(photoMapper::toVM)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PhotoVM> getAllPhotos() {
        return photoRepository.findAll().stream()
                .map(photoMapper::toVM)
                .collect(Collectors.toList());
    }

    public PhotoVM updatePhoto(UUID id, PhotoVM photoVM) {
        if (!photoRepository.existsById(id)) {
            throw new EntityNotFoundException("Photo not found with id: " + id);
        }

        Post post = postRepository.findById(photoVM.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + photoVM.getPostId()));

        Photo photo = photoMapper.toEntity(photoVM);
        photo.setId(id);
        photo.setPost(post);
        Photo updatedPhoto = photoRepository.save(photo);
        return photoMapper.toVM(updatedPhoto);
    }

    public void deletePhoto(UUID id) {
        if (!photoRepository.existsById(id)) {
            throw new EntityNotFoundException("Photo not found with id: " + id);
        }
        photoRepository.deleteById(id);
    }

    public void deletePhotosByPostId(UUID postId) {
        photoRepository.deleteByPostId(postId);
    }
}