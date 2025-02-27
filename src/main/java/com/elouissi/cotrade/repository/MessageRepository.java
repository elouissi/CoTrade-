package com.elouissi.cotrade.repository;

import com.elouissi.cotrade.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySender_Id(UUID senderId);
    List<Message> findByReceiver_Id(UUID receiverId);
    List<Message> findBySender_IdAndReceiver_Id(UUID senderId, UUID receiverId);
}