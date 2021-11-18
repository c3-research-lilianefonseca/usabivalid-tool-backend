package com.unicap.tcc.usability.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sas")
public @Data
class AmazonSASProperties {
    private String from;
    private String fromName;
}
