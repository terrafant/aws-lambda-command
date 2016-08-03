package com.uay.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class S3EventJsonHandler implements RequestHandler<S3Event, String> {

    AmazonS3Client client = new AmazonS3Client(
            new BasicAWSCredentials("ACCESS_KEY", "SECRET_KEY"));
    ObjectMapper mapper = new ObjectMapper();

    public String handleRequest(S3Event s3Event, Context context) {
        System.out.println("s3Event.toJson() = " + s3Event.toJson());
        String bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
        System.out.println("bucketName = " + bucketName);

        String key = s3Event.getRecords().get(0).getS3().getObject().getKey();
        System.out.println("key = " + key);

        S3Object s3Object = client.getObject(bucketName, key);
        System.out.println("s3Object = " + s3Object);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();

        try {
            Map<String, Object> jsonMap = mapper.readValue(objectContent, Map.class);
            System.out.println("jsonMap = " + jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s3Object.getObjectMetadata().getContentType();
    }
}

