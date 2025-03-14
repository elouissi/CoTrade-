package com.elouissi.cotrade.repository;

import com.elouissi.cotrade.domain.Conversation;
import com.elouissi.cotrade.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByConversation_Id(UUID conversationId);

    @Query("SELECT m FROM Message m WHERE m.conversation.sender.id = :userId OR m.conversation.receiver.id = :userId")
    List<Message> findMessagesByUserId(@Param("userId") UUID userId);

    @Query("SELECT m FROM Message m WHERE (m.conversation.sender.id = :senderId AND m.conversation.receiver.id = :receiverId) OR (m.conversation.sender.id = :receiverId AND m.conversation.receiver.id = :senderId)")
    List<Message> findMessagesBetweenUsers(@Param("senderId") UUID senderId, @Param("receiverId") UUID receiverId);

    @Query("SELECT m FROM Message m WHERE m.conversation.sender.id = :senderId AND m.conversation.receiver.id = :receiverId AND m.conversation.post.id = :postId")
    List<Message> findByConversationSenderAndReceiverAndPost(@Param("senderId") UUID senderId, @Param("receiverId") UUID receiverId, @Param("postId") UUID postId);

    Optional<Message> findTopByConversationOrderByTimeDesc(Conversation conversation);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.conversation = :conversation AND m.isRead = false")
    long countByConversationAndIsReadFalse(@Param("conversation") Conversation conversation);
}