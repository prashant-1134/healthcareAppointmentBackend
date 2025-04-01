package com.healthcaremanagement.healthcare1.controller;


import com.healthcaremanagement.healthcare1.service.NotificationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+" + phoneNumber;  // Ensure the '+' is included
        }
        notificationService.sendSms(phoneNumber, message);
        return "✅ SMS Sent successfully to " + phoneNumber;
    }
}

