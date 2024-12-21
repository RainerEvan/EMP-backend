package com.emp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String generateEmailBody(String recipientName, String monthYear) {
        StringBuilder emailBody = new StringBuilder();
    
        emailBody.append("Dear ").append(recipientName).append(",\n\n");
        emailBody.append("Bersama ini terlampir Slip Gaji bulan ").append(monthYear).append(".\n");
        emailBody.append("Atas perhatiannya, saya ucapkan terima kasih.\n\n");
        emailBody.append("Best Regards,\n");
        emailBody.append("Manajemen CV Bangun Kerja Keras");
    
        return emailBody.toString();
    }

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] pdfAttachment, String attachmentName) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            // Attach the PDF
            helper.addAttachment(attachmentName, new ByteArrayDataSource(pdfAttachment, "application/pdf"));

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }
}
