package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Conversation;
import com.elouissi.cotrade.domain.Message;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.repository.AppUserRepository;
import com.elouissi.cotrade.repository.ConversationRepository;
import com.elouissi.cotrade.repository.MessageRepository;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.service.DTO.ConversationDTO;
import com.elouissi.cotrade.service.DTO.MessageDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageMapper messageMapper;


    public MessageDTO createMessage(MessageDTO messageDTO, UUID senderId, UUID receiverId) {
        Message message = new Message();
        message.setBody(messageDTO.getBody());
        message.setAttachment(messageDTO.getAttachment());
        message.setRead(messageDTO.isRead());

        Conversation conversation = conversationRepository.findById(messageDTO.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        message.setConversation(conversation);
        message.setSenderId(senderId); // Définir l'expéditeur du message
        message.setReceiverId(receiverId); // Définir le destinataire du message
        message.setTime(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        return convertToDTO(savedMessage);
    }

    public List<MessageDTO> getMessagesByConversation(UUID conversationId, UUID currentUserId) {
        List<MessageDTO> dtos = messageRepository.findByConversation_Id(conversationId)
                .stream()
                .sorted(Comparator.comparing(Message::getTime))
                .map(message -> {
                    MessageDTO dto = convertToDTO(message);
                    // Vérifier si senderId est null avant de comparer
                    dto.setSentByCurrentUser(message.getSenderId() != null && message.getSenderId().equals(currentUserId));
                    return dto;
                })
                .collect(Collectors.toList());
        return dtos;
    }    public MessageDTO updateMessage(UUID id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setBody(messageDTO.getBody());
        message.setAttachment(messageDTO.getAttachment());
        message.setRead(messageDTO.isRead());

        if (messageDTO.getConversationId() != null) {
            Conversation conversation = conversationRepository.findById(messageDTO.getConversationId())
                    .orElseThrow(() -> new RuntimeException("conversation not found"));
            message.setConversation(conversation);
        }

        Message updatedMessage = messageRepository.save(message);
        return convertToDTO(updatedMessage);
    }

    public List<MessageDTO> getMessagesBetweenUsers(UUID senderId, UUID receiverId, UUID postId) {
        List<Conversation> conversations = new ArrayList<>();

        if (postId != null) {
            // If postId is specified, find conversations for this specific post in both directions
            conversations.addAll(conversationRepository.findBySender_IdAndReceiver_IdAndPost_Id(senderId, receiverId, postId));
            conversations.addAll(conversationRepository.findBySender_IdAndReceiver_IdAndPost_Id(receiverId, senderId, postId));
        } else {
            // Otherwise find all conversations between these users in both directions
            conversations.addAll(conversationRepository.findBySender_IdAndReceiver_Id(senderId, receiverId));
            conversations.addAll(conversationRepository.findBySender_IdAndReceiver_Id(receiverId, senderId));
        }

        List<Message> allMessages = new ArrayList<>();

        // Get all messages from these conversations
        for (Conversation conversation : conversations) {
            allMessages.addAll(messageRepository.findByConversation_Id(conversation.getId()));
        }

        // Sort messages by time and convert to DTOs
        return allMessages.stream()
                .sorted(Comparator.comparing(Message::getTime))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getReceivedMessages(UUID userId) {
        // Trouver toutes les conversations où l'utilisateur est le destinataire
        List<Conversation> receivedConversations = conversationRepository.findByReceiver_Id(userId);

        List<Message> receivedMessages = new ArrayList<>();
        for (Conversation conversation : receivedConversations) {
            receivedMessages.addAll(messageRepository.findByConversation_Id(conversation.getId()));
        }

        return receivedMessages
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MessageDTO> getSentMessages(UUID userId) {
        // Trouver toutes les conversations où l'utilisateur est l'expéditeur
        List<Conversation> sentConversations = conversationRepository.findBySender_Id(userId);

        List<Message> sentMessages = new ArrayList<>();
        for (Conversation conversation : sentConversations) {
            sentMessages.addAll(messageRepository.findByConversation_Id(conversation.getId()));
        }

        return sentMessages
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public MessageDTO markAsRead(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setRead(true);
        Message updatedMessage = messageRepository.save(message);

        return convertToDTO(updatedMessage);
    }
    public List<MessageDTO> getAll(){
        return messageRepository.findAll()
                .stream().map(messageMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setBody(message.getBody());
        dto.setAttachment(message.getAttachment());
        dto.setRead(message.isRead());
        dto.setTime(message.getTime());
        dto.setConversationId(message.getConversation().getId());
        dto.setSenderId(message.getSenderId());
        dto.setReceiverId(message.getReceiverId());


        return dto;
    }
}