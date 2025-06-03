package com.team5.issue_tracker.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean
  public AmazonS3 amazonS3() {
    return AmazonS3ClientBuilder.standard()
        .withRegion(Regions.fromName(region))
        .build();
  }
}

