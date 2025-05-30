#!/bin/bash

echo "🚀 Starting Spring Boot app..."

# .env 파일 로드
if [ -f /home/ubuntu/.env ]; then
  echo "📦 Loading environment variables from .env"
  source /home/ubuntu/.env
else
  echo "❌ .env file not found!"
  exit 1
fi

# 애플리케이션 실행
cd /home/ubuntu
JAR_NAME=app.jar

nohup java \
  -Dspring.profiles.active=dev \
  -DJWT_ACCESS_KEY=$JWT_ACCESS_KEY \
  -DJWT_REFRESH_KEY=$JWT_REFRESH_KEY \
  -DAWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
  -DAWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
  -DAWS_REGION=$AWS_REGION \
  -Dcloud.aws.region.static=$AWS_REGION \
  -Dcloud.aws.s3.bucket=$S3_BUCKET_NAME \
  -Dcloud.aws.s3.url=$S3_URL_NAME \
  -Dspring.datasource.password=$DB_PASSWORD \
  -jar "$JAR_NAME" > app.log 2>&1 &
