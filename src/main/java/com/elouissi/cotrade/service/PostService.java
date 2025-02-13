package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.service.DTO.PostDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.PostMapper;
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
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostDTO createPost(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
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

    public PostDTO updatePost(UUID id, PostDTO postDTO) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
        postDTO.setId(id);
        Post post = postMapper.toEntity(postDTO);
        Post updatedPost = postRepository.save(post);
        return postMapper.toDto(updatedPost);
    }

    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }
}