package com.upgrad.userservice.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class S3Service {

    private AmazonS3 s3Client;

    @Value("${bmc.bucket.name}")
    private String bucketName;

    @Autowired
    ObjectMetadata metadata;

    @PostConstruct
    public void init() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAXIAR4W6CKPOJ7ZMQ",
                "JT+H6WhRh8zyNr7T1Kz90o4hQJ3tp0EAuGdXRMDQ");
        s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public boolean uploadFileToS3(String doctorId, MultipartFile file) throws IOException {
        Map<String,String> metadataMap = new HashMap<>();
        if(!s3Client.doesBucketExistV2(bucketName)){
            Bucket bucket = s3Client.createBucket(bucketName);

        }
        String subFolder = doctorId+"/"+file.getOriginalFilename();
        s3Client.putObject(bucketName, subFolder, file.getInputStream(), metadata);
        return true;
    }
}
