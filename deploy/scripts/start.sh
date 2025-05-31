#!/bin/bash
echo "> [START] Spring Boot App 실행"

# .env 복사 (처음 배포된 환경변수 템플릿을 실제로 적용)
cp /home/ubuntu/app/application.env /home/ubuntu/.env

# 환경변수 불러오기
source /home/ubuntu/.env

# 실행 중이면 종료
PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  kill -9 $PID
  echo "> 이전 프로세스 종료됨: $PID"
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
  -Dspring.web.resources.static-locations=file:/home/ubuntu/app/frontend/ \
  -jar /home/ubuntu/app/app.jar > /home/ubuntu/app.log 2>&1 &

echo "> Spring Boot 실행 완료"
