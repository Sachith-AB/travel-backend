# WebSocket Real-Time Chat Testing Guide 🚀

## Testing Methods

### Method 1: Using the HTML Test Client (Easiest) ✅

1. **Open the test file:**

   - Navigate to `d:\Project\travel\websocket-test.html`
   - Open it in your web browser (Chrome, Firefox, Edge, etc.)

2. **Test Connection:**

   - Click the **"Connect"** button
   - Status should change to "✅ Connected"
   - Message log will show connection success

3. **Subscribe to Messages:**

   - Enter a User ID (e.g., `2`) in "Subscribe to User ID"
   - Click **"Subscribe"** button
   - You'll now receive any messages sent to User ID 2

4. **Send a Test Message:**

   - Sender ID: `1`
   - Receiver ID: `2`
   - Message Content: `Hello! This is a test message.`
   - Click **"Send Message"**

5. **Test Real-Time Communication:**
   - Open the HTML file in **TWO browser tabs**
   - **Tab 1**: Subscribe to User 1 messages
   - **Tab 2**: Subscribe to User 2 messages
   - Send message from User 1 to User 2 in Tab 1
   - You should see it received in Tab 2 instantly! 🎉

---

### Method 2: Using Postman (For REST API Testing) 📮

#### Test 1: Save Message via REST API

```http
POST http://localhost:5454/api/messages
Content-Type: application/json

{
  "senderId": 1,
  "receiverId": 2,
  "content": "Hello from REST API!",
  "seen": false
}
```

#### Test 2: Get Messages for a User

```http
GET http://localhost:5454/api/messages/2
```

---

### Method 3: Using Postman WebSocket Request (Advanced) 🔌

1. **Create a new WebSocket Request in Postman:**

   - Click "New" → "WebSocket Request"
   - URL: `ws://localhost:5454/ws`
   - Protocol: `SockJS`

2. **Connect to WebSocket:**

   - Click "Connect"
   - Wait for connection success

3. **Send STOMP Frame to Subscribe:**

   ```
   CONNECT
   accept-version:1.0,1.1,2.0

   ^@
   ```

4. **Subscribe to a topic:**

   ```
   SUBSCRIBE
   id:sub-0
   destination:/topic/messages/2

   ^@
   ```

5. **Send a message:**

   ```
   SEND
   destination:/app/sendMessage
   content-type:application/json

   {"senderId":1,"receiverId":2,"content":"Test from Postman"}
   ^@
   ```

---

### Method 4: Using Browser DevTools (For Debugging) 🔧

1. Open Chrome DevTools (F12)
2. Go to **Network** tab
3. Filter by **WS** (WebSocket)
4. Look for the WebSocket connection to `localhost:5454/ws`
5. Click on it to see:
   - Messages sent
   - Messages received
   - Connection status
   - Frame data

---

## Expected Results ✅

### Successful Connection:

- Status: "✅ Connected"
- No error messages in console
- WebSocket connection shown in Network tab

### Successful Message Send:

- Message appears in message log
- Database record created (check via REST API GET)
- No error in terminal/console

### Successful Real-Time Delivery:

- Subscriber receives message instantly
- Message shows sender ID, receiver ID, content, and timestamp
- Both sender and receiver see the message

---

## Troubleshooting 🔍

### Connection Fails (400 Error):

- ✅ Already fixed! But if it happens:
  - Check Security Config allows WebSocket endpoints
  - Verify CORS configuration
  - Check WebSocket config has `setAllowedOriginPatterns("*")`

### Messages Not Received:

- Verify you're subscribed to correct topic: `/topic/messages/{receiverId}`
- Check receiverId matches in message and subscription
- Verify message is being saved (check REST API)

### Database Errors:

- ✅ Already fixed with PostgreSQL configuration!
- Verify database connection in application logs

---

## Quick Test Script 📝

**Test Scenario: Two Users Chatting**

1. **Setup:**

   - Open `websocket-test.html` in Browser Tab 1
   - Open `websocket-test.html` in Browser Tab 2

2. **Tab 1 (User 1):**

   - Connect
   - Subscribe to User ID: `1`
   - Ready to receive messages

3. **Tab 2 (User 2):**

   - Connect
   - Subscribe to User ID: `2`
   - Send message: From `2` to `1` with content "Hi User 1!"

4. **Expected Result:**

   - Tab 1 shows: "📥 New Message Received from User 2"
   - Message appears instantly in Tab 1's message log

5. **Reply:**
   - In Tab 1, send message: From `1` to `2` with content "Hi User 2!"
   - Tab 2 should receive it instantly

---

## Verify Messages in Database 💾

### Using REST API:

```bash
# Get all messages for User 2
curl http://localhost:5454/api/messages/2
```

### Using PostgreSQL Client:

```sql
SELECT * FROM messages ORDER BY timestamp DESC LIMIT 10;
```

---

## WebSocket Endpoints Summary 📡

| Endpoint                   | Description                                 |
| -------------------------- | ------------------------------------------- |
| `ws://localhost:5454/ws`   | WebSocket connection endpoint (with SockJS) |
| `/app/sendMessage`         | Send message destination                    |
| `/topic/messages/{userId}` | Subscribe to receive messages for user      |

---

## Success Indicators ✅

- [ ] Connection status shows "Connected"
- [ ] Can subscribe to topics without errors
- [ ] Can send messages without errors
- [ ] Messages saved in database (verify via REST API)
- [ ] Real-time delivery works (subscriber receives instantly)
- [ ] Multiple tabs can communicate
- [ ] No errors in browser console
- [ ] No errors in Spring Boot logs

---

## Next Steps 🚀

Once testing is successful, you can:

1. Integrate with your React/Vue/Angular frontend
2. Add authentication (JWT tokens)
3. Add message notifications
4. Add typing indicators
5. Add read receipts
6. Add message history loading

---

**Ready to test! 🎉**

Start with Method 1 (HTML Test Client) - it's the easiest way to verify everything works!
