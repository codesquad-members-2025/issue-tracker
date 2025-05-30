package com.team5.issue_tracker.common.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.HttpMethod;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3UploadService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String createPresignedUrl(String key) {
    Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 5); // 5분 유효
    GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
        .withMethod(HttpMethod.PUT)
        .withExpiration(expiration);

    return amazonS3.generatePresignedUrl(request).toString();
  }

  public String getAccessUrl(String key) {
    return "https://" + bucket + ".s3.amazonaws.com/" + key;
  }
}
