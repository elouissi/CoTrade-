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
        return ResponseEntity.ok(messageService.createMessage(messageDTO));
    }

    @GetMapping("/between/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(
            @PathVariable UUID senderId,
            @PathVariable UUID receiverId) {
        return ResponseEntity.ok(messageService.getMessagesBetweenUsers(senderId, receiverId));
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getReceivedMessages(userId));
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable UUID userId) {
        return ResponseEntity.ok(messageService.getSentMessages(userId));
    }

}