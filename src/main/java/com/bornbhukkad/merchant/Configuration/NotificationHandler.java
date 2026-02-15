package com.bornbhukkad.merchant.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bornbhukkad.merchant.Service.RestaurantServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationHandler extends TextWebSocketHandler {

    private static final Logger logger =
        LoggerFactory.getLogger(NotificationHandler.class);

    private final Map<String, WebSocketSession> sessions =
        new ConcurrentHashMap<>();
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

    	System.out.println("📩 WS message received: " + message.getPayload());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(message.getPayload());

        String merchantId = payload.get("merchantId").asText();

        sessions.put(merchantId, session);

        System.out.println("✅ Merchant registered in WS map: " + merchantId);
        System.out.println("🧠 Active WS sessions: " + sessions.keySet());
    }

    public void sendNotificationToMerchant(String merchantId, Object notification)
            throws Exception {

        System.out.println("📤 Attempting to send WS event");
        System.out.println("📌 MerchantId requested: " + merchantId);
        System.out.println("🧠 Active sessions: " + sessions.keySet());

        WebSocketSession session = sessions.get(merchantId);

        if (session == null) {
            System.out.println("❌ No WS session found for merchant: " + merchantId);
            return;
        }

        if (!session.isOpen()) {
            System.out.println("❌ WS session is CLOSED for merchant: " + merchantId);
            return;
        }

        String payload = objectMapper.writeValueAsString(notification);


        session.sendMessage(new TextMessage(payload));

        System.out.println("✅ WS event sent to merchant: " + merchantId);
    }


    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) {
        sessions.entrySet()
            .removeIf(entry -> entry.getValue().equals(session));
        logger.info("Session closed: {}", session.getId());
    }
}
