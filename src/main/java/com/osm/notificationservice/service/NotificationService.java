package com.osm.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private KafkaTemplate<String,  Object> kafkaTemplate;


//    @KafkaListener(topics = "booking-confirmation-email", groupId = "notification-group")
//    public void handleEmailNotification(Map<String, String> messageData) {
//        // Extract the information from messageData and send the email
//        String email = messageData.get("email");
//        String emailSubject = "Booking Confirmation";
//        String emailBody = String.format(
//                "Dear %s %s,\n\nYour booking for %s at %s on %s has been confirmed.",
//                messageData.get("firstname"), messageData.get("lastname"),
//                messageData.get("tableName"), messageData.get("time"), messageData.get("date")
//        );
//        System.out.println(emailBody);
//        // Call your email service to send the email using emailBody
//        emailService.sendBookingConfirmation(email, emailSubject, emailBody);
//
//    }
//
//    @KafkaListener(topics = "booking-confirmation-whatsapp", groupId = "notification-group")
//    public void handleWhatsAppNotification(Map<String, String> messageData) {
//        // Extract the information from messageData and send the WhatsApp message
//        String phoneNumber = messageData.get("phonenumber");
//        String whatsappMessage = String.format(
//                "Hello %s %s, your booking for %s at %s on %s has been confirmed.",
//                messageData.get("firstname"), messageData.get("lastname"),
//                messageData.get("tableName"), messageData.get("time"), messageData.get("date")
//        );
//        System.out.println(whatsappMessage);
//        // Call your WhatsApp service to send the WhatsApp message using whatsappMessage
//        whatsAppService.sendWhatsAppMessage(phoneNumber, whatsappMessage);
//    }
//}

//    @KafkaListener(topics = "booking-confirmation-email", groupId = "notification-group")
//    public void handleEmailNotification(Map<String, String> messageData) {
//        try {
//            String email = messageData.get("email");
//            if (email == null || email.isEmpty()) {
//                throw new IllegalArgumentException("Email address is missing or invalid.");
//            }
//
//            String emailSubject = "Booking Confirmation";
//            String emailBody = String.format(
//                    "Dear %s %s,\n\nYour booking for %s at %s on %s has been confirmed.",
//                    messageData.get("firstname"), messageData.get("lastname"),
//                    messageData.get("tableName"), messageData.get("time"), messageData.get("date")
//            );
//            System.out.println(emailBody);
//
//            emailService.sendBookingConfirmation(email, emailSubject, emailBody);
//        } catch (Exception e) {
//            System.err.println("Error handling email notification: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    @KafkaListener(topics = "booking-confirmation-whatsapp", groupId = "notification-group")
//    public void handleWhatsAppNotification(Map<String, String> messageData) {
//        try {
//            String phoneNumber = messageData.get("phonenumber");
//            if (phoneNumber == null || phoneNumber.isEmpty()) {
//                throw new IllegalArgumentException("Phone number is missing or invalid.");
//            }
//
//            String whatsappMessage = String.format(
//                    "Hello %s %s, your booking for %s at %s on %s has been confirmed.",
//                    messageData.get("firstname"), messageData.get("lastname"),
//                    messageData.get("tableName"), messageData.get("time"), messageData.get("date")
//            );
//            System.out.println(whatsappMessage);
//
//            whatsAppService.sendWhatsAppMessage(phoneNumber, whatsappMessage);
//        } catch (Exception e) {
//            System.err.println("Error handling WhatsApp notification: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
    @KafkaListener(topics = "booking-confirmation-email", groupId = "notification-group")
    public void handleEmailNotification(Map<String, String> messageData) {
        try {
            String email = messageData.get("email");
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email address is missing or invalid.");
            }
            String emailSubject = "Booking Confirmation";
            String emailBody = String.format(
                    "Dear %s %s,\n\nYour booking for %s at %s on %s has been confirmed.",
                    messageData.get("firstname"), messageData.get("lastname"),
                    messageData.get("tableName"), messageData.get("time"), messageData.get("date")
            );

            emailService.sendBookingConfirmation(email, emailSubject, emailBody);

            // Send success response back to the scheduling service
            kafkaTemplate.send("notification-response", Map.of("status", "success"));

        } catch (Exception e) {
            // Send failure response back to the scheduling service
            kafkaTemplate.send("notification-response", Map.of("status", "failure"));
        }
    }

        @KafkaListener(topics = "booking-confirmation-whatsapp", groupId = "notification-group")
        public void handleWhatsAppNotification(Map<String, String> messageData) {
            try {
                String phoneNumber = messageData.get("phonenumber");
                if (phoneNumber == null || phoneNumber.isEmpty()) {
                    throw new IllegalArgumentException("Phone number is missing or invalid.");
                }
                String whatsappMessage = String.format(
                        "Hello %s %s, your booking for %s at %s on %s has been confirmed.",
                        messageData.get("firstname"), messageData.get("lastname"),
                        messageData.get("tableName"), messageData.get("time"), messageData.get("date")
                );

                whatsAppService.sendWhatsAppMessage(phoneNumber, whatsappMessage);

                // Send success response back to the scheduling service
                kafkaTemplate.send("notification-response", Map.of("status", "success"));

            } catch (Exception e) {
                // Send failure response back to the scheduling service
                kafkaTemplate.send("notification-response", Map.of("status", "failure"));
            }
        }
    }