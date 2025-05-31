#!/bin/bash
echo "> [START] 실행 시작"

cd /home/ubuntu/app

# 기존 실행 중인 프로세스 종료
echo "> 기존 백엔드 종료 시도"
pkill -f 'java -jar' || true

# .env 파일 생성
echo "> 환경변수 파일 생성"
cat <<EOF > .env
JWT_ACCESS_KEY=${JWT_ACCESS_KEY}
JWT_REFRESH_KEY=${JWT_REFRESH_KEY}
AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
AWS_REGION=${AWS_REGION}
cloud.aws.region.static=${AWS_REGION}
cloud.aws.s3.bucket=${S3_BUCKET_NAME}
EOF

# .env 적용
echo "> 환경변수 적용"
source .env

# 백엔드 애플리케이션 실행
echo "> 백엔드 애플리케이션 실행"
nohup java \
  -Dspring.profiles.active=dev \
  -DJWT_ACCESS_KEY=$JWT_ACCESS_KEY \
  -DJWT_REFRESH_KEY=$JWT_REFRESH_KEY \
  -DAWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
  -DAWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
  -Dcloud.aws.region.static=$AWS_REGION \
  -Dcloud.aws.s3.bucket=$S3_BUCKET_NAME \
  -jar /home/ubuntu/app/app.jar > /home/ubuntu/app/app.log 2>&1 &
