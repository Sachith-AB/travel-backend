package com.travel.travel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.Models.Message;
import com.travel.travel.Service.MessageService;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageRestController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessagesByUser(receiverId);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/conversation/{userId1}/{userId2}")
    public ResponseEntity<List<Message>> getConversation(
            @PathVariable Long userId1, 
            @PathVariable Long userId2) {
        List<Message> messages = messageService.getConversation(userId1, userId2);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<Long>> getConversationPartners(@PathVariable Long userId) {
        List<Long> partners = messageService.getConversationPartners(userId);
        return ResponseEntity.ok(partners);
    }
}
