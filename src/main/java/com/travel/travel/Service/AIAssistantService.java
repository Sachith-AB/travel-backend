package com.travel.travel.Service;

import com.travel.travel.DTO.ChatRequest;
import com.travel.travel.DTO.ChatResponse;

public interface AIAssistantService {
    ChatResponse processMessage(ChatRequest request);
}
