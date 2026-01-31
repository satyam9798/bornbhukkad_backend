package com.bornbhukkad.merchant.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
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

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(message.getPayload());

        String merchantId = payload.get("merchantId").asText();
        sessions.put(merchantId, session);

        logger.info("Merchant connected: {}", merchantId);
    }

    public void sendNotificationToMerchant(String merchantId, Object data)
            throws Exception {

        WebSocketSession session = sessions.get(merchantId);

        if (session != null && session.isOpen()) {
            ObjectMapper mapper = new ObjectMapper();
            session.sendMessage(
                new TextMessage(mapper.writeValueAsString(data))
            );
        }
    }

    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) {
        sessions.entrySet()
            .removeIf(entry -> entry.getValue().equals(session));
        logger.info("Session closed: {}", session.getId());
    }
}
