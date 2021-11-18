package com.unicap.tcc.usability.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "s3")
public @Data
class BucketS3Properties {
    private String bucket;
    private String region;
}
