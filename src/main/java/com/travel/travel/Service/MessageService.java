package com.travel.travel.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Message;
import com.travel.travel.Repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }
    
    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageRepository.findConversationBetweenUsers(userId1, userId2);
    }
    
    public List<Long> getConversationPartners(Long userId) {
        return messageRepository.findAllConversationPartners(userId);
    }
}
