package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.Message;
import com.elouissi.cotrade.repository.AppUserRepository;
import com.elouissi.cotrade.repository.MessageRepository;
import com.elouissi.cotrade.service.DTO.MessageDTO;
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

class MessageServiceTest {

    @Mock private MessageRepository messageRepository;
    @Mock private AppUserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    private MessageDTO messageDTO;
    private AppUser sender;
    private AppUser receiver;
    private UUID senderId;
    private UUID receiverId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        senderId = UUID.randomUUID();
        receiverId = UUID.randomUUID();

        sender = new AppUser();
        sender.setId(senderId);

        receiver = new AppUser();
        receiver.setId(receiverId);

        messageDTO = new MessageDTO(); // Constructeur par défaut
        messageDTO.setBody("Hello");


        // Mock des réponses
        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

//    @Test
//    void createMessage_ShouldReturnMessageDTO() {
//        MessageDTO result = messageService.createMessage(messageDTO);
//
//        assertNotNull(result);
//        assertEquals(messageDTO.getBody(), result.getBody());
//
//
//        verify(userRepository, times(1)).findById(senderId);
//        verify(userRepository, times(1)).findById(receiverId);
//        verify(messageRepository, times(1)).save(any(Message.class));
//    }
}