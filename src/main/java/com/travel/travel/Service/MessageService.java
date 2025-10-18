package com.travel.travel.Service;

import com.travel.travel.Models.Message;
import com.travel.travel.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }
}
