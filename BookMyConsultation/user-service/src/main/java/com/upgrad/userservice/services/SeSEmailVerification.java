package com.upgrad.userservice.services;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import javax.annotation.PostConstruct;

@Component
public class SeSEmailVerification {
    private SesClient sesClient;

    @PostConstruct
    public void init() {
        String accessKey="AKIAXIAR4W6CKPOJ7ZMQ";
        String secretKey="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";
        StaticCredentialsProvider staticCredentials = StaticCredentialsProvider
            .create(AwsBasicCredentials.create(accessKey, secretKey));
        sesClient = SesClient
            .builder()
            .credentialsProvider(staticCredentials)
            .region(Region.US_EAST_1)
            .build();
    }

    public void sendVerificationEmail(String emailId){
        sesClient.verifyEmailAddress(req->req.emailAddress(emailId));
    }
}
