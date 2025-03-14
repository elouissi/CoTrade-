package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.service.DTO.MessageDTO;
import com.elouissi.cotrade.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.createMessage(messageDTO, messageDTO.getSenderId(), messageDTO.getReceiverId()));
    }

    @GetMapping("/conversation/{conversationId}/{userId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByConversation(@PathVariable UUID conversationId, @PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getMessagesByConversation(conversationId, userId));
    }

    @GetMapping("/between/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(
            @PathVariable UUID senderId,
            @PathVariable UUID receiverId) {
        return ResponseEntity.ok(messageService.getMessagesBetweenUsers(senderId, receiverId, null));
    }

    @GetMapping("/between/{senderId}/{receiverId}/{postId}")
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(
            @PathVariable UUID senderId,
            @PathVariable UUID receiverId,
            @PathVariable UUID postId) {
        return ResponseEntity.ok(messageService.getMessagesBetweenUsers(senderId, receiverId, postId));
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getReceivedMessages(userId));
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getSentMessages(userId));
    }

    @PatchMapping("/{messageId}/read")
    public ResponseEntity<MessageDTO> markAsRead(@PathVariable UUID messageId) {
        return ResponseEntity.ok(messageService.markAsRead(messageId));
    }
}