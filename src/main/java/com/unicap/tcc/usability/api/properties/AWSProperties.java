package com.unicap.tcc.usability.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
public @Data
class AWSProperties {
    private String accessKeyId;
    private String secretAccessKey;
}