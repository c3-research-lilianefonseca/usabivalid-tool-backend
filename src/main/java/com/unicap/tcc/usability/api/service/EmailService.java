package com.unicap.tcc.usability.api.service;

import java.io.ByteArrayOutputStream;

public interface EmailService {
    void sendText(String from, String[] to, String subject, String body);
    void sendHTML(String from, String[] to, String subject, String body);
    void sendAttachment(String from, String[] to, String subject, String body, ByteArrayOutputStream src);
}
