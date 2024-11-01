package com.bornbhukkad.merchant.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.bornbhukkad.merchant.Service.RestaurantServiceImpl;

@Component
public class NotificationHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String merchantId = message.getPayload();
        sessions.put(merchantId, session);
        logger.info("Merchant connected with ID: " + merchantId);    
        }

    public void sendNotificationToMerchant(String merchantId, String notification) throws Exception {
        WebSocketSession session = sessions.get(merchantId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(notification));
        }
    }
}
