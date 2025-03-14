package com.elouissi.cotrade.repository;

import com.elouissi.cotrade.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    List<Conversation> findBySender_IdOrReceiver_Id(UUID senderId, UUID receiverId);

    // Find conversations where sender and receiver match the given IDs and for specific post
    List<Conversation> findBySenderIdAndReceiverIdAndPostId(UUID senderId, UUID receiverId, UUID postId);

    List<Conversation> findBySender_IdAndReceiver_IdAndPost_Id(UUID senderId, UUID receiverId, UUID postId);
    List<Conversation> findBySender_IdAndReceiver_Id(UUID senderId, UUID receiverId);

    List<Conversation> findByReceiver_Id(UUID receiverId);
    List<Conversation> findBySender_Id(UUID senderId);
    List<Conversation> findByPostId(UUID postId);

    // Find conversations involving a specific user for a specific post
    @Query("SELECT c FROM Conversation c WHERE c.post.id = :postId AND (c.sender.id = :userId OR c.receiver.id = :userId)")
    List<Conversation> findByPostIdAndUserId(@Param("postId") UUID postId, @Param("userId") UUID userId);
}