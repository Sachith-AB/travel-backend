package com.travel.travel.Controller;

import com.travel.travel.DTO.ChatRequest;
import com.travel.travel.DTO.ChatResponse;
import com.travel.travel.Service.AIAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai-assistant")
@CrossOrigin(origins = "*")
public class AIAssistantController {

    @Autowired
    private AIAssistantService aiAssistantService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            ChatResponse response = aiAssistantService.processMessage(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("I apologize, but I'm having trouble processing your request. Please try again.", 
                            java.time.LocalDateTime.now().toString()));
        }
    }
}
