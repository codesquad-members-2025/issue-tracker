#!/bin/bash
echo "> Start script running..."

# .env 파일 생성 (GitHub Secrets에서 전달된 값을 직접 여기서 설정)
cat <<EOF > /home/ubuntu/app/.env
JWT_ACCESS_KEY=${JWT_ACCESS_KEY}
JWT_REFRESH_KEY=${JWT_REFRESH_KEY}
AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
AWS_REGION=${AWS_REGION}
cloud.aws.region.static=${AWS_REGION}
cloud.aws.s3.bucket=${S3_BUCKET}
EOF

source /home/ubuntu/app/.env

echo "> Kill previous app..."
pkill -f 'java -jar' || true

echo "> Start app..."
nohup java \
  -Dspring.profiles.active=dev \
  -DJWT_ACCESS_KEY=$JWT_ACCESS_KEY \
  -DJWT_REFRESH_KEY=$JWT_REFRESH_KEY \
  -DAWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
  -DAWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
  -Dcloud.aws.region.static=$AWS_REGION \
  -Dcloud.aws.s3.bucket=$S3_BUCKET \
  -jar /home/ubuntu/app/app.jar > /home/ubuntu/app/app.log 2>&1 &
