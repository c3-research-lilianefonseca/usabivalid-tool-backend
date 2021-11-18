package com.unicap.tcc.usability.api.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class AmazonSESService {
    static final String FROM = "rodrigo.2018150572@unicap.br";
    static final String FROMNAME = "Usability Validation Instrument";
    static final String SMTP_USERNAME = "AKIAVEETHJ3RMC46IWUI";
    static final String SMTP_PASSWORD = "BCa7g75XoKPoH65Pu8CL8u5CMDaXH4kjdyBAOOFryVVk";
    static final String HOST = "email-smtp.sa-east-1.amazonaws.com";
    static final int PORT = 587;

    public void send(String to, String subject, String body) {
        // Create a Properties object to contain connection configuration information.

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        try (Transport transport = session.getTransport()) {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM, FROMNAME));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setContent(body, "text/html");
            System.out.println("Sending...");
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
}
