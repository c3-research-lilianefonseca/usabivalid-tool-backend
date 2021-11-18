package com.unicap.tcc.usability.api.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.unicap.tcc.usability.api.properties.BucketS3Properties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;


//@Service
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

//    private final AmazonS3 s3client;
//    private final BucketS3Properties bucketConfig;

//    public void uploadFile(String localFilePath) {
//
//        try {
//            File file = new File(localFilePath);
//            s3client.putObject(new PutObjectRequest(bucketConfig.getBucket(), "teste.jpg", file));
//        } catch (AmazonServiceException e) {
//            LOG.info("AmazonServiceException: " + e.getErrorMessage());
//            LOG.info("Status code: " + e.getStatusCode());
//        } catch (AmazonClientException e) {
//            LOG.info("AmazonClientException: " + e.getMessage());
//        }
//    }
//
//    public byte[] downloadFile(String filename) {
//        try {
//            S3Object s3Object = s3client.getObject(bucketConfig.getBucket(), filename);
//            S3ObjectInputStream inputStream = s3Object.getObjectContent();
//            byte[] bytes;
//            bytes = StreamUtils.copyToByteArray(inputStream);
//            return bytes;
//        } catch (IOException e) {
//            LOG.info("AmazonClientException: " + e.getMessage());
//        }
//        return new byte[]{};
//    }
}
