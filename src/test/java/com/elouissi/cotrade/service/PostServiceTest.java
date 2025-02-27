package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.repository.PhotoRepository;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.service.DTO.PhotoDTO;
import com.elouissi.cotrade.service.DTO.PostDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.PostMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostDTO postDTO;
    private Photo photo;
    private PhotoDTO photoDTO;
    private UUID postId;

    @BeforeEach
    void setUp() {
        postId = UUID.randomUUID();

        post = new Post();
        post.setId(postId);
        post.setTitle("Test Post");
        post.setDescription("Test Description");

        photo = new Photo();
        photo.setId(UUID.randomUUID());
        photo.setFilePath("test-url.jpg");
        photo.setPost(post);

        photoDTO = new PhotoDTO();
        photoDTO.setId(photo.getId());
        photoDTO.setFilePath(photo.getFilePath());

        postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setTitle("Test Post");
        postDTO.setDescription("Test Description");
        postDTO.setPhotos(Collections.singletonList(photoDTO));

        post.setPhotos(Collections.singletonList(photo));
    }

    @Test
    void createPost_WithPhotos_Success() {
        // Arrange
        when(postMapper.toEntity(postDTO)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.photoDTOToPhoto(any(PhotoDTO.class))).thenReturn(photo);
        when(photoRepository.saveAll(anyList())).thenReturn(Collections.singletonList(photo));
        when(postMapper.toDto(post)).thenReturn(postDTO);

        // Act
        PostDTO result = postService.createPost(postDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postId);
        assertThat(result.getPhotos()).hasSize(1);
        verify(photoRepository).saveAll(anyList());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void getPost_ExistingId_ReturnsPost() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postDTO);

        // Act
        PostDTO result = postService.getPost(postId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postId);
        verify(postRepository).findById(postId);
    }

    @Test
    void getPost_NonExistingId_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(postRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> postService.getPost(nonExistingId));
        verify(postRepository).findById(nonExistingId);
    }

    @Test
    void getAllPosts_ReturnsAllPosts() {
        // Arrange
        List<Post> posts = Collections.singletonList(post);
        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toDto(post)).thenReturn(postDTO);

        // Act
        List<PostDTO> results = postService.getAllPosts();

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(postId);
        verify(postRepository).findAll();
    }

    @Test
    void updatePost_ExistingPost_Success() {
        // Arrange
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toEntity(postDTO)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDTO);
        when(postMapper.photoDTOToPhoto(any(PhotoDTO.class))).thenReturn(photo);
        when(photoRepository.saveAll(anyList())).thenReturn(Collections.singletonList(photo));

        // Act
        PostDTO result = postService.updatePost(postId, postDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postId);
        verify(photoRepository).deleteByPostId(postId);
        verify(photoRepository).saveAll(anyList());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void deletePost_ExistingPost_Success() {
        // Arrange
        when(postRepository.existsById(postId)).thenReturn(true);

        // Act
        postService.deletePost(postId);

        // Assert
        verify(photoRepository).deleteByPostId(postId);
        verify(postRepository).deleteById(postId);
    }

    @Test
    void deletePost_NonExistingPost_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(postRepository.existsById(nonExistingId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> postService.deletePost(nonExistingId));
        verify(postRepository).existsById(nonExistingId);
        verify(photoRepository, never()).deleteByPostId(any());
        verify(postRepository, never()).deleteById(any());
    }
}