package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.Conversation;
import com.elouissi.cotrade.repository.ConversationRepository;
import com.elouissi.cotrade.service.DTO.ConversationDTO;
import com.elouissi.cotrade.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationRepository conversationRepository;

    @PostMapping
    public ResponseEntity<ConversationDTO> createConversation(@RequestBody ConversationDTO conversationDTO) {
        return ResponseEntity.ok(conversationService.createConversation(conversationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDTO> getConversationById(@PathVariable UUID id) {
        return ResponseEntity.ok(conversationService.getConversationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConversationDTO>> getConversationsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(conversationService.getConversationsByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable UUID id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/find-or-create")
    public ResponseEntity<?> findOrCreateConversation(@RequestBody ConversationDTO conversationDTO) {
        UUID senderId = conversationDTO.getSenderId();
        UUID receiverId = conversationDTO.getReceiverId();
        UUID postId = conversationDTO.getPostId();

//        // Prevent creating a conversation with yourself
        if (senderId.equals(receiverId)) {
            return ResponseEntity.badRequest().body("Cannot create a conversation with yourself");
        }

        // Check for existing conversations in both directions
        List<ConversationDTO> existingConversations = conversationService.findConversationBySenderReceiverAndPost(
                senderId, receiverId, postId
        );

        // If a conversation exists, return it
        if (!existingConversations.isEmpty()) {
            return ResponseEntity.ok(existingConversations.get(0));
        }

        // Otherwise, create a new conversation
        try {
            return ResponseEntity.ok(conversationService.createConversation(conversationDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/byPost/{postId}")
    public ResponseEntity<List<ConversationDTO>> getConversationsByPostId(@PathVariable UUID postId) {
        List<ConversationDTO> conversations = conversationService.findConversationsByPostId(postId);
        return ResponseEntity.ok(conversations);
    }
}