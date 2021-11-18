package com.unicap.tcc.usability.api.service.sendgrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.sendgrid.SendGrid;

@Configuration
@PropertySource("classpath:application.yml")
public class SendGridConfig {

    @Value("${sendgrid.api.key}") String sendGridAPIKey;

    @Bean
    public SendGrid getSendGrid(){
        return new SendGrid(sendGridAPIKey);
    }

}
