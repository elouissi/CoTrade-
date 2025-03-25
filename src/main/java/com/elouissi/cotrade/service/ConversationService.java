package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Conversation;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.domain.Message;
import com.elouissi.cotrade.repository.ConversationRepository;
import com.elouissi.cotrade.repository.AppUserRepository;
import com.elouissi.cotrade.repository.PostRepository;
import com.elouissi.cotrade.repository.MessageRepository;
import com.elouissi.cotrade.service.DTO.ConversationDTO;
import com.elouissi.cotrade.service.DTO.UserDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.ConversationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;
import java.util.ArrayList;

@Service
@Transactional
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    public ConversationDTO createConversation(ConversationDTO conversationDTO) {
        // Prevent creating a conversation with yourself
        if (conversationDTO.getSenderId().equals(conversationDTO.getReceiverId())) {
            throw new IllegalArgumentException("Cannot create a conversation with yourself");
        }

        // Check for existing conversations in both directions
        List<Conversation> existingConversations = new ArrayList<>();
        existingConversations.addAll(conversationRepository.findBySenderIdAndReceiverIdAndPostId(
                conversationDTO.getSenderId(), conversationDTO.getReceiverId(), conversationDTO.getPostId()
        ));
        existingConversations.addAll(conversationRepository.findBySenderIdAndReceiverIdAndPostId(
                conversationDTO.getReceiverId(), conversationDTO.getSenderId(), conversationDTO.getPostId()
        ));

        if (!existingConversations.isEmpty()) {
            // Return existing conversation
            return convertToDTO(existingConversations.get(0));
        }

        // Create new conversation
        Conversation conversation = new Conversation();

        AppUser sender = userRepository.findById(conversationDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        AppUser receiver = userRepository.findById(conversationDTO.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Post post = postRepository.findById(conversationDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        conversation.setSender(sender);
        conversation.setReceiver(receiver);
        conversation.setPost(post);

        Conversation savedConversation = conversationRepository.save(conversation);
        return convertToDTO(savedConversation);
    }
    public List<ConversationDTO> findConversationsByPostId(UUID postId) {
        return conversationRepository.findByPostId(postId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ConversationDTO getConversationById(UUID id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        return convertToDTOWithDetails(conversation);
    }

    public List<ConversationDTO> getConversationsByUserId(UUID userId) {
        List<Conversation> conversations = conversationRepository.findBySender_IdOrReceiver_Id(userId, userId);
        return conversations.stream()
                .map(this::convertToDTOWithDetails)
                .sorted((a, b) -> {
                    if (a.getUpdatedAt() == null) return 1;
                    if (b.getUpdatedAt() == null) return -1;
                    return b.getUpdatedAt().compareTo(a.getUpdatedAt());
                })
                .collect(Collectors.toList());
    }

    public List<ConversationDTO> findConversationBySenderReceiverAndPost(UUID senderId, UUID receiverId, UUID postId) {
        List<ConversationDTO> result = new ArrayList<>();

        List<Conversation> conversations = conversationRepository.findBySenderIdAndReceiverIdAndPostId(senderId, receiverId, postId);
        conversations.addAll(conversationRepository.findBySenderIdAndReceiverIdAndPostId(receiverId, senderId, postId));

        return conversations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteConversation(UUID id) {
        conversationRepository.deleteById(id);
    }

    private ConversationDTO convertToDTO(Conversation conversation) {
        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getId());
        dto.setSenderId(conversation.getSender().getId());
        dto.setReceiverId(conversation.getReceiver().getId());
        dto.setPostId(conversation.getPost().getId());
        return dto;
    }

    private ConversationDTO convertToDTOWithDetails(Conversation conversation) {
        ConversationDTO dto = convertToDTO(conversation);

        // Ajouter des informations supplémentaires
        dto.setSenderName(conversation.getSender().getName());
        dto.setReceiverName(conversation.getReceiver().getName());
        dto.setPostTitle(conversation.getPost().getTitle());

        // Récupérer le dernier message
        Optional<Message> lastMessageOpt = messageRepository.findTopByConversationOrderByTimeDesc(conversation);
        if (lastMessageOpt.isPresent()) {
            Message lastMessage = lastMessageOpt.get();
            dto.setLastMessage(lastMessage.getBody());
            dto.setUpdatedAt(lastMessage.getTime());

            // Count unread messages for the conversation
            long unreadCount = messageRepository.countByConversationAndIsReadFalse(conversation);
            dto.setUnreadCount((int) unreadCount);
        }

        if (!conversation.getPost().getPhotos().isEmpty()) {
            dto.setPostImage(conversation.getPost().getPhotos().get(0).getFilePath());
        }

        return dto;
    }
    public List<ConversationDTO> getAll(){
        return conversationRepository.findAll()
                .stream().map(this::convertToDTOWithDetails)
                .collect(Collectors.toList());
    }
}