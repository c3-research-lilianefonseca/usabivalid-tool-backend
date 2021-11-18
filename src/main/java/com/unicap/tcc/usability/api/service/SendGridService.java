package com.unicap.tcc.usability.api.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class SendGridService implements EmailService {

    private SendGrid sendGridClient;

    @Autowired
    public SendGridService(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;

    }

    @Override
    public void sendText(String from, String[] to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/plain", body), null);
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
    }

    @Override
    public void sendHTML(String from, String[] to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/html", body), null);
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
    }

    @Override
    public void sendAttachment(String from, String[] to, String subject, String body, ByteArrayOutputStream src) {

        byte[] bytes = src.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Response response = sendEmail(from, to, subject, new Content("text/html", body), inputStream);
        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
    }



    private Response sendEmail(String from, String[] to, String subject, Content content, InputStream attachment) {
        Mail mail = new Mail();
        mail.setFrom(new Email(from));
        mail.setSubject(subject);
        mail.addContent(content);
        mail.setReplyTo(new Email("fsbs@cin.ufpe.br"));

        if(attachment != null){
            mail.addAttachments(new Attachments.Builder("", attachment)
                    .withType("application/pdf")
                    .build());
        }

        for(String s : to){
            Personalization personalization = new Personalization();
            personalization.addTo(new Email(s));
            mail.addPersonalization(personalization);
        }

        Request request = new Request();
        Response response = null;

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGridClient.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
