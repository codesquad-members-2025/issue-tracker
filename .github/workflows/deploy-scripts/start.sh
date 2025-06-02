#!/bin/bash
cd /home/ubuntu/app/bundle
source .env

echo "🚀 애플리케이션 실행 시작..."
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
  -jar app.jar > /home/ubuntu/app/app.log 2>&1 &

sleep 3
echo "📄 서버 로그 출력 "
tail -f /home/ubuntu/app/app.log || echo "⚠️ 로그 파일이 아직 생성되지 않았습니다."
