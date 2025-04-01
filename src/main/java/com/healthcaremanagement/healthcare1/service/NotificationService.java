package com.healthcaremanagement.healthcare1.service;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void initTwilio() {
        if (accountSid != null && authToken != null) {
            Twilio.init(accountSid, authToken);
            logger.info("✅ Twilio API initialized successfully.");
        } else {
            logger.error("❌ Twilio credentials are missing! Check application.properties.");
        }
    }

    public void sendSms(String patientPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(patientPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();

            logger.info("✅ SMS Sent: '{}' to {}", messageBody, patientPhoneNumber);
        } catch (ApiException e) {
            logger.error("❌ Failed to send SMS: {}", e.getMessage());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        // 🔹 Manually trigger SMS sending on app startup
//        String testPhoneNumber = "+919209158061"; // Replace with a valid recipient number
//        sendSms(testPhoneNumber, "Hello! This is a test SMS from your Spring Boot application. 🚀");
    }
}
