package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Message;
import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.repository.AppUserRepository;
import com.elouissi.cotrade.repository.MessageRepository;
import com.elouissi.cotrade.service.DTO.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AppUserRepository userRepository;

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setBody(messageDTO.getBody());
        message.setAttachment(messageDTO.getAttachment());
        message.setRead(messageDTO.isRead());

        AppUser sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        AppUser receiver = userRepository.findById(messageDTO.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        message.setSender(sender);
        message.setReceiver(receiver);

        Message savedMessage = messageRepository.save(message);
        return convertToDTO(savedMessage);
    }

    public MessageDTO updateMessage(UUID id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setBody(messageDTO.getBody());
        message.setAttachment(messageDTO.getAttachment());
        message.setRead(messageDTO.isRead());

        if (messageDTO.getSenderId() != null) {
            AppUser sender = userRepository.findById(messageDTO.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            message.setSender(sender);
        }

        if (messageDTO.getReceiverId() != null) {
            AppUser receiver = userRepository.findById(messageDTO.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));
            message.setReceiver(receiver);
        }

        Message updatedMessage = messageRepository.save(message);
        return convertToDTO(updatedMessage);
    }

    public List<MessageDTO> getMessagesBetweenUsers(UUID senderId, UUID receiverId) {
        return messageRepository.findBySender_IdAndReceiver_Id(senderId, receiverId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MessageDTO> getReceivedMessages(UUID userId) {
        return messageRepository.findByReceiver_Id(userId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<MessageDTO> getSentMessages(UUID userId) {
        return messageRepository.findBySender_Id(userId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setBody(message.getBody());
        dto.setAttachment(message.getAttachment());
        dto.setRead(message.isRead());
        dto.setSenderId(message.getSender().getId());
        dto.setReceiverId(message.getReceiver().getId());
        return dto;
    }

}