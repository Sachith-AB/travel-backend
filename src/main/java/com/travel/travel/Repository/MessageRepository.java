package com.travel.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.travel.Models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Find conversation between two users
    @Query("SELECT m FROM Message m WHERE (m.senderId = :userId1 AND m.receiverId = :userId2) OR (m.senderId = :userId2 AND m.receiverId = :userId1) ORDER BY m.timestamp ASC")
    List<Message> findConversationBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    
    // Find all messages received by a user
    List<Message> findByReceiverId(Long receiverId);
    
    // Find all unique conversations for a user
    @Query("SELECT DISTINCT CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END FROM Message m WHERE m.senderId = :userId OR m.receiverId = :userId")
    List<Long> findAllConversationPartners(@Param("userId") Long userId);
}
