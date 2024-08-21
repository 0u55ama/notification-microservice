package com.osm.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private WhatsAppService whatsAppService;

    @KafkaListener(topics = "booking-confirmation-email", groupId = "notification-group")
    public void handleEmailNotification(Map<String, String> messageData) {
        // Extract the information from messageData and send the email
        String email = messageData.get("email");
        String emailSubject = "Booking Confirmation";
        String emailBody = String.format(
                "Dear %s %s,\n\nYour booking for %s at %s on %s has been confirmed.",
                messageData.get("firstname"), messageData.get("lastname"),
                messageData.get("tableName"), messageData.get("time"), messageData.get("date")
        );
        System.out.println(emailBody);
        // Call your email service to send the email using emailBody
        emailService.sendBookingConfirmation(email, emailSubject, emailBody);

    }

    @KafkaListener(topics = "booking-confirmation-whatsapp", groupId = "notification-group")
    public void handleWhatsAppNotification(Map<String, String> messageData) {
        // Extract the information from messageData and send the WhatsApp message
        String phoneNumber = messageData.get("phonenumber");
        String whatsappMessage = String.format(
                "Hello %s %s, your booking for %s at %s on %s has been confirmed.",
                messageData.get("firstname"), messageData.get("lastname"),
                messageData.get("tableName"), messageData.get("time"), messageData.get("date")
        );
        System.out.println(whatsappMessage);
        // Call your WhatsApp service to send the WhatsApp message using whatsappMessage
        whatsAppService.sendWhatsAppMessage(phoneNumber, whatsappMessage);
    }
}