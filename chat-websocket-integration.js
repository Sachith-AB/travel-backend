// WebSocket Chat Integration Example
// Copy this code to integrate WebSocket chat in your React/Vue/Angular app

// ============================================
// 1. Install Dependencies (if needed)
// ============================================
// npm install sockjs-client stompjs

// ============================================
// 2. Import Libraries
// ============================================
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

// Or if using CDN in HTML:
// <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

// ============================================
// 3. WebSocket Service Class
// ============================================
class ChatWebSocketService {
    constructor(baseUrl = 'http://localhost:5454') {
        this.baseUrl = baseUrl;
        this.stompClient = null;
        this.subscriptions = {};
        this.connected = false;
    }

    // Connect to WebSocket
    connect(onConnectCallback, onErrorCallback) {
        const socket = new SockJS(`${this.baseUrl}/ws`);
        this.stompClient = Stomp.over(socket);

        // Optional: Disable debug logs in production
        // this.stompClient.debug = () => {};

        this.stompClient.connect(
            {}, // headers (can add auth tokens here)
            (frame) => {
                console.log('✅ WebSocket Connected:', frame);
                this.connected = true;
                if (onConnectCallback) onConnectCallback(frame);
            },
            (error) => {
                console.error('❌ WebSocket Connection Error:', error);
                this.connected = false;
                if (onErrorCallback) onErrorCallback(error);
            }
        );
    }

    // Disconnect from WebSocket
    disconnect(callback) {
        if (this.stompClient && this.connected) {
            this.stompClient.disconnect(() => {
                console.log('👋 WebSocket Disconnected');
                this.connected = false;
                this.subscriptions = {};
                if (callback) callback();
            });
        }
    }

    // Send a message
    sendMessage(senderId, receiverId, content) {
        if (!this.connected || !this.stompClient) {
            console.error('❌ Cannot send message: Not connected');
            return false;
        }

        const message = {
            senderId: senderId,
            receiverId: receiverId,
            content: content
        };

        console.log('📤 Sending message:', message);
        
        this.stompClient.send(
            '/app/sendMessage',
            {},
            JSON.stringify(message)
        );

        return true;
    }

    // Subscribe to receive messages for a specific user
    subscribeToMessages(userId, onMessageCallback) {
        if (!this.connected || !this.stompClient) {
            console.error('❌ Cannot subscribe: Not connected');
            return null;
        }

        // Unsubscribe if already subscribed
        if (this.subscriptions[userId]) {
            this.subscriptions[userId].unsubscribe();
        }

        const topic = `/topic/messages/${userId}`;
        console.log(`🔔 Subscribing to: ${topic}`);

        const subscription = this.stompClient.subscribe(topic, (message) => {
            const receivedMessage = JSON.parse(message.body);
            console.log('📥 Message received:', receivedMessage);
            
            if (onMessageCallback) {
                onMessageCallback(receivedMessage);
            }
        });

        this.subscriptions[userId] = subscription;
        return subscription;
    }

    // Unsubscribe from user messages
    unsubscribeFromMessages(userId) {
        if (this.subscriptions[userId]) {
            this.subscriptions[userId].unsubscribe();
            delete this.subscriptions[userId];
            console.log(`🔕 Unsubscribed from User ${userId}`);
        }
    }

    // Check connection status
    isConnected() {
        return this.connected;
    }
}

// ============================================
// 4. Usage Examples
// ============================================

// Example 1: Basic Usage
const chatService = new ChatWebSocketService();

// Connect
chatService.connect(
    () => {
        console.log('Connected successfully!');
        
        // Subscribe to receive messages for current user (e.g., userId = 1)
        chatService.subscribeToMessages(1, (message) => {
            console.log('New message:', message);
            // Update UI with new message
            displayMessage(message);
        });
    },
    (error) => {
        console.error('Connection failed:', error);
        // Show error to user
    }
);

// Send a message
function sendChatMessage(receiverId, text) {
    const currentUserId = 1; // Get from your auth system
    chatService.sendMessage(currentUserId, receiverId, text);
}

// Disconnect when done
function cleanup() {
    chatService.disconnect(() => {
        console.log('Cleanup complete');
    });
}

// ============================================
// 5. React Hook Example
// ============================================
import { useState, useEffect, useRef } from 'react';

function useWebSocketChat(userId) {
    const [messages, setMessages] = useState([]);
    const [connected, setConnected] = useState(false);
    const chatServiceRef = useRef(null);

    useEffect(() => {
        // Initialize WebSocket service
        chatServiceRef.current = new ChatWebSocketService();

        // Connect
        chatServiceRef.current.connect(
            () => {
                setConnected(true);
                
                // Subscribe to messages
                chatServiceRef.current.subscribeToMessages(userId, (newMessage) => {
                    setMessages((prev) => [...prev, newMessage]);
                });
            },
            (error) => {
                console.error('Connection error:', error);
                setConnected(false);
            }
        );

        // Cleanup on unmount
        return () => {
            if (chatServiceRef.current) {
                chatServiceRef.current.disconnect();
            }
        };
    }, [userId]);

    const sendMessage = (receiverId, content) => {
        if (chatServiceRef.current && connected) {
            chatServiceRef.current.sendMessage(userId, receiverId, content);
        }
    };

    return { messages, connected, sendMessage };
}

// Usage in React component:
function ChatComponent() {
    const currentUserId = 1; // Get from auth context
    const { messages, connected, sendMessage } = useWebSocketChat(currentUserId);
    const [messageText, setMessageText] = useState('');
    const [receiverId, setReceiverId] = useState(2);

    const handleSend = () => {
        if (messageText.trim()) {
            sendMessage(receiverId, messageText);
            setMessageText('');
        }
    };

    return (
        <div>
            <div>Status: {connected ? '✅ Connected' : '❌ Disconnected'}</div>
            
            <div>
                <h3>Messages:</h3>
                {messages.map((msg, idx) => (
                    <div key={idx}>
                        From: {msg.senderId} - {msg.content} ({msg.timestamp})
                    </div>
                ))}
            </div>

            <div>
                <input 
                    value={messageText}
                    onChange={(e) => setMessageText(e.target.value)}
                    placeholder="Type message..."
                />
                <button onClick={handleSend} disabled={!connected}>
                    Send
                </button>
            </div>
        </div>
    );
}

// ============================================
// 6. Vue.js Composition API Example
// ============================================
import { ref, onMounted, onUnmounted } from 'vue';

export function useWebSocketChat(userId) {
    const messages = ref([]);
    const connected = ref(false);
    let chatService = null;

    onMounted(() => {
        chatService = new ChatWebSocketService();

        chatService.connect(
            () => {
                connected.value = true;
                chatService.subscribeToMessages(userId, (newMessage) => {
                    messages.value.push(newMessage);
                });
            },
            (error) => {
                console.error('Connection error:', error);
                connected.value = false;
            }
        );
    });

    onUnmounted(() => {
        if (chatService) {
            chatService.disconnect();
        }
    });

    const sendMessage = (receiverId, content) => {
        if (chatService && connected.value) {
            chatService.sendMessage(userId, receiverId, content);
        }
    };

    return { messages, connected, sendMessage };
}

// ============================================
// 7. Angular Service Example
// ============================================
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ChatWebSocketService {
    private chatService: ChatWebSocketService;
    private messagesSubject = new BehaviorSubject<any[]>([]);
    private connectedSubject = new BehaviorSubject<boolean>(false);

    public messages$: Observable<any[]> = this.messagesSubject.asObservable();
    public connected$: Observable<boolean> = this.connectedSubject.asObservable();

    constructor() {
        this.chatService = new ChatWebSocketService();
    }

    connect(userId: number): void {
        this.chatService.connect(
            () => {
                this.connectedSubject.next(true);
                this.chatService.subscribeToMessages(userId, (message) => {
                    const currentMessages = this.messagesSubject.value;
                    this.messagesSubject.next([...currentMessages, message]);
                });
            },
            (error) => {
                console.error('Connection error:', error);
                this.connectedSubject.next(false);
            }
        );
    }

    sendMessage(senderId: number, receiverId: number, content: string): void {
        this.chatService.sendMessage(senderId, receiverId, content);
    }

    disconnect(): void {
        this.chatService.disconnect();
        this.connectedSubject.next(false);
    }
}

// ============================================
// 8. Plain JavaScript Example (No Framework)
// ============================================
const chatService = new ChatWebSocketService();
const currentUserId = 1;

// Connect when page loads
document.addEventListener('DOMContentLoaded', () => {
    chatService.connect(
        () => {
            updateStatus('Connected');
            chatService.subscribeToMessages(currentUserId, displayMessage);
        },
        (error) => {
            updateStatus('Connection failed');
        }
    );
});

// Send message on button click
document.getElementById('sendBtn').addEventListener('click', () => {
    const receiverId = document.getElementById('receiverId').value;
    const message = document.getElementById('messageInput').value;
    chatService.sendMessage(currentUserId, receiverId, message);
});

function displayMessage(message) {
    const messageDiv = document.createElement('div');
    messageDiv.textContent = `From ${message.senderId}: ${message.content}`;
    document.getElementById('messages').appendChild(messageDiv);
}

function updateStatus(status) {
    document.getElementById('status').textContent = status;
}

// Export for use in other files
export default ChatWebSocketService;
