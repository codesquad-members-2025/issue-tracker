#!/bin/bash

echo "🚀 Starting Spring Boot app..."
cd /home/ubuntu/deploy

# 환경변수는 ~/.bashrc에 등록되어 있음
nohup java \
  -Dspring.profiles.active=dev \
  -DJWT_ACCESS_KEY=$JWT_ACCESS_KEY \
  -DJWT_REFRESH_KEY=$JWT_REFRESH_KEY \
  -DAWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
  -DAWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
  -DAWS_REGION=$AWS_REGION \
  -Dcloud.aws.region.static=$AWS_REGION \
  -Dcloud.aws.s3.bucket=$S3_BUCKET_NAME \
  -Dspring.datasource.password=$DB_PASSWORD \
  -jar IssueTracker-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
