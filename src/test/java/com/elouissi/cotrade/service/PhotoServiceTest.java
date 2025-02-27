package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.repository.PhotoRepository;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.service.PhotoService;
import com.elouissi.cotrade.web.rest.VM.PhotoVM;
import com.elouissi.cotrade.web.rest.VM.mapper.PhotoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PhotoServiceTest {

    @Mock private PhotoRepository photoRepository;
    @Mock private PostRepository postRepository;
    @Mock private PhotoMapper photoMapper;

    @InjectMocks
    private PhotoService photoService;

    private PhotoVM photoVM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        photoVM = new PhotoVM();
        photoVM.setPostId(UUID.randomUUID());
        photoVM.setFilePath("photo.jpg");
    }

    @Test
    void createPhoto_ShouldAttachToPost() {
        Post mockPost = new Post();
        when(postRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockPost));
        when(photoMapper.toEntity(any(PhotoVM.class))).thenReturn(new Photo());
        when(photoRepository.save(any(Photo.class))).thenReturn(new Photo());

        photoService.createPhoto(photoVM);

        verify(postRepository, times(1)).findById(any(UUID.class));
    }
}