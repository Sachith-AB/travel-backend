package com.travel.travel.Controller;

import com.travel.travel.Models.Message;
import com.travel.travel.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*") // for Postman or frontend
public class MessageRestController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    @GetMapping("/{receiverId}")
    public List<Message> getMessages(@PathVariable Long receiverId) {
        return messageService.getMessagesByUser(receiverId);
    }
}
