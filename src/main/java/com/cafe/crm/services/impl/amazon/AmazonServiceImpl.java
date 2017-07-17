package com.cafe.crm.services.impl.amazon;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cafe.crm.services.interfaces.amazon.AmazonService;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonServiceImpl implements AmazonService {

    @Override
    public AmazonS3 getConnection(String accessKey, String secretKey) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials
                (accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.DEFAULT_REGION)
                .build();
    }

    @Override
    public void putObject(AmazonS3 s3client, File file, String bucketName, String keyName) {
        s3client.putObject(new PutObjectRequest(bucketName, keyName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public void getObject(AmazonS3 s3client, String bucketName, String keyName) {
        s3client.getObject(new GetObjectRequest(bucketName, keyName));
    }

    @Override
    public void saveObjectToFile(AmazonS3 s3client, File file, String bucketName, String keyName) {
        ObjectMetadata object = s3client.getObject
                (new GetObjectRequest(bucketName, keyName), file);
    }
}
