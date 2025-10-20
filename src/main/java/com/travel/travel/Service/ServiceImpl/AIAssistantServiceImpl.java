package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.DTO.ChatRequest;
import com.travel.travel.DTO.ChatResponse;
import com.travel.travel.Service.AIAssistantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AIAssistantServiceImpl implements AIAssistantService {

    @Value("${openai.api.key:}")
    private String openAiApiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String model;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    public AIAssistantServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ChatResponse processMessage(ChatRequest request) {
        try {
            // If OpenAI API key is not configured, use rule-based responses
            if (openAiApiKey == null || openAiApiKey.trim().isEmpty()) {
                return getRuleBasedResponse(request.getMessage());
            }

            // Use OpenAI API for intelligent responses
            return getOpenAIResponse(request.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatResponse(
                    "I apologize, but I'm having trouble processing your request. Please try again or contact support.",
                    LocalDateTime.now().toString()
            );
        }
    }

    private ChatResponse getOpenAIResponse(String userMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openAiApiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", Arrays.asList(
                    Map.of("role", "system", "content", getSystemPrompt()),
                    Map.of("role", "user", "content", userMessage)
            ));
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 500);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    String content = (String) message.get("content");
                    return new ChatResponse(content, LocalDateTime.now().toString());
                }
            }

            return getRuleBasedResponse(userMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return getRuleBasedResponse(userMessage);
        }
    }

    private String getSystemPrompt() {
        return """
                You are Travel.lk Assistant, a helpful AI assistant for Travel.lk - a comprehensive travel platform for Sri Lanka.
                
                About Travel.lk:
                - We help travelers explore the beautiful island of Sri Lanka
                - Our platform offers hotel bookings, tour guide services, and vehicle rentals
                - Users can create custom tours, book accommodations, and hire professional guides
                - We support secure payments through Stripe
                - Our system includes real-time chat for communication with service providers
                
                Your capabilities:
                1. Help users plan their Sri Lanka travel itinerary
                2. Provide information about hotels, tour guides, and vehicle rentals
                3. Assist with booking processes and account management
                4. Answer questions about Sri Lankan destinations, culture, and attractions
                5. Guide users through the platform features
                
                Guidelines:
                - Be friendly, helpful, and professional
                - Provide accurate information about Sri Lanka and our services
                - If you don't know something, be honest and suggest contacting customer support
                - Keep responses concise but informative
                - Encourage users to explore our platform features
                - For specific bookings or account issues, direct users to the appropriate section
                
                Popular Sri Lankan destinations to recommend:
                - Colombo (capital city, shopping, food)
                - Kandy (cultural capital, Temple of the Tooth)
                - Galle (historic fort, beaches)
                - Ella (scenic train rides, hiking)
                - Sigiriya (ancient rock fortress)
                - Nuwara Eliya (tea plantations, cool climate)
                - Yala National Park (wildlife safari)
                - Mirissa (whale watching, beaches)
                - Anuradhapura (ancient ruins)
                - Polonnaruwa (ancient city)
                """;
    }

    private ChatResponse getRuleBasedResponse(String message) {
        String lowerMessage = message.toLowerCase();
        String response;

        if (lowerMessage.contains("hotel") || lowerMessage.contains("accommodation") || lowerMessage.contains("stay")) {
            response = "I can help you find the perfect hotel in Sri Lanka! 🏨\n\n" +
                    "Our platform offers a wide range of accommodations:\n" +
                    "• Luxury hotels and resorts\n" +
                    "• Budget-friendly guesthouses\n" +
                    "• Boutique hotels\n\n" +
                    "You can browse hotels by clicking 'Reserve' → 'Hotels' in the menu. " +
                    "Would you like recommendations for a specific area?";
        } else if (lowerMessage.contains("guide") || lowerMessage.contains("tour guide")) {
            response = "Looking for a professional tour guide? 🗺️\n\n" +
                    "Our platform connects you with experienced, licensed tour guides who can:\n" +
                    "• Create customized itineraries\n" +
                    "• Provide local insights and cultural knowledge\n" +
                    "• Ensure a safe and memorable experience\n\n" +
                    "Visit 'Reserve' → 'Tour Guides' to browse available guides and book!";
        } else if (lowerMessage.contains("vehicle") || lowerMessage.contains("car") || lowerMessage.contains("transport")) {
            response = "Need transportation for your Sri Lanka adventure? 🚗\n\n" +
                    "We offer:\n" +
                    "• Cars with drivers\n" +
                    "• Vans for group travel\n" +
                    "• Luxury vehicles\n\n" +
                    "Check out 'Reserve' → 'Vehicles' to see available options!";
        } else if (lowerMessage.contains("book") || lowerMessage.contains("reservation")) {
            response = "Making a booking is easy! 📅\n\n" +
                    "Steps:\n" +
                    "1. Browse our services (Hotels, Guides, or Vehicles)\n" +
                    "2. Select your preferred option\n" +
                    "3. Choose dates and complete details\n" +
                    "4. Proceed to secure payment\n" +
                    "5. Receive instant confirmation\n\n" +
                    "Need help with a specific booking?";
        } else if (lowerMessage.contains("payment") || lowerMessage.contains("pay")) {
            response = "We use Stripe for secure payments! 💳\n\n" +
                    "Accepted payment methods:\n" +
                    "• Credit/Debit cards\n" +
                    "• International cards\n\n" +
                    "All transactions are encrypted and secure. You'll receive a confirmation email after successful payment.";
        } else if (lowerMessage.contains("cancel") || lowerMessage.contains("refund")) {
            response = "For cancellations and refunds:\n\n" +
                    "• Review our cancellation policy before booking\n" +
                    "• Contact the service provider through our chat system\n" +
                    "• Or reach out to customer support for assistance\n\n" +
                    "Refund policies vary by service provider.";
        } else if (lowerMessage.contains("account") || lowerMessage.contains("profile") || lowerMessage.contains("password")) {
            response = "Account management help:\n\n" +
                    "• Update your profile by clicking your email in the top right\n" +
                    "• Reset password through 'Reset password' quick action\n" +
                    "• View your bookings in your dashboard\n\n" +
                    "Need more specific help with your account?";
        } else if (lowerMessage.contains("destination") || lowerMessage.contains("place") || lowerMessage.contains("visit")) {
            response = "Sri Lanka is full of amazing destinations! 🌴\n\n" +
                    "Popular spots:\n" +
                    "• Sigiriya - Ancient rock fortress\n" +
                    "• Ella - Scenic hill country\n" +
                    "• Galle - Historic fort city\n" +
                    "• Kandy - Cultural capital\n" +
                    "• Yala - Wildlife safaris\n" +
                    "• Mirissa - Beaches & whale watching\n\n" +
                    "Which type of experience interests you most?";
        } else if (lowerMessage.contains("help") || lowerMessage.contains("support")) {
            response = "I'm here to help! 😊\n\n" +
                    "I can assist you with:\n" +
                    "• Finding and booking hotels\n" +
                    "• Hiring tour guides\n" +
                    "• Renting vehicles\n" +
                    "• Planning your itinerary\n" +
                    "• Account management\n\n" +
                    "What would you like help with?";
        } else if (lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey")) {
            response = "Hello! 👋 Welcome to Travel.lk!\n\n" +
                    "I'm your personal travel assistant for exploring Sri Lanka. " +
                    "I can help you with hotels, tour guides, vehicles, and travel planning.\n\n" +
                    "What would you like to know about?";
        } else if (lowerMessage.contains("thank")) {
            response = "You're welcome! 😊\n\n" +
                    "Feel free to ask if you have any other questions. " +
                    "Have a wonderful time planning your Sri Lanka adventure!";
        } else {
            response = "I'm here to help you with your Sri Lanka travel plans! 🌺\n\n" +
                    "I can assist with:\n" +
                    "• Hotel bookings 🏨\n" +
                    "• Tour guide services 🗺️\n" +
                    "• Vehicle rentals 🚗\n" +
                    "• Travel planning & destinations 🌴\n" +
                    "• Account & booking help 💼\n\n" +
                    "What would you like to know more about?";
        }

        return new ChatResponse(response, LocalDateTime.now().toString());
    }
}
