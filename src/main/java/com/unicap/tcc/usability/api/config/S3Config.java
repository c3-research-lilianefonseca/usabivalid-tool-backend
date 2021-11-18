package com.unicap.tcc.usability.api.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.unicap.tcc.usability.api.properties.AWSProperties;
import com.unicap.tcc.usability.api.properties.BucketS3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class S3Config {
//
//    private final AWSProperties awsProperties;
//    private final BucketS3Properties bucketS3Properties;
//
//    @Bean
//    public AmazonS3 s3client() {
//        BasicAWSCredentials awsCred = new BasicAWSCredentials(awsProperties.getAccessKeyId(), awsProperties.getSecretAccessKey());
//        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(bucketS3Properties.getRegion()))
//                            .withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();
//    }
}
