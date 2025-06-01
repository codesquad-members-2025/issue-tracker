#!/bin/bash

cd /home/ubuntu/app
source application.env

# 기존 프로세스 종료
PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  kill -9 $PID
  echo "✅ 이전 프로세스 종료됨: $PID"
fi

# 새로 실행
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
  -jar app.jar > /home/ubuntu/app.log 2>&1 &
