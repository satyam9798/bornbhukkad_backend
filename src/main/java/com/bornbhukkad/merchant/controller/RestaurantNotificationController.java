package com.bornbhukkad.merchant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bornbhukkad.merchant.Configuration.NotificationHandler;
import com.bornbhukkad.merchant.Configuration.WebSocketConfig;
import com.bornbhukkad.merchant.Service.RestaurantService;
import com.bornbhukkad.merchant.dto.NotificationRequest;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = {"Authorization", "Content-Type"})
public class RestaurantNotificationController {

    
	@Autowired
    private NotificationHandler notificationHandler;
	
    @Autowired
    public void NotificationController(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }
    
    @PostMapping("/api/notify")
    public ResponseEntity<String> notifyMerchant(@RequestParam("merchantId") String merchantId,@RequestBody NotificationRequest request) throws Exception {

        String notification = request.getOrderDetails();
        notificationHandler.sendNotificationToMerchant(merchantId, notification);
        return ResponseEntity.ok("Notification sent successfully.");
    }
}

