#!/bin/bash

echo "📦 [run-dev-local] 백엔드 디렉토리로 이동"
cd be || { echo "❌ be 디렉토리 진입 실패"; exit 1; }

echo "📦 [run-dev-local] Gradle 빌드 시작..."
./gradlew clean build || { echo "❌ Gradle 빌드 실패"; exit 1; }

cd ..

echo "🌱 [run-dev-local] 환경 변수 로드 중..."
if [ ! -f .env.dev ]; then
  echo "❌ .env.dev 파일이 존재하지 않습니다!"
  exit 1
fi
source .env.dev

echo "🚀 [run-dev-local] 애플리케이션 실행 중..."
java \
  -Dspring.profiles.active=dev \
  -DJWT_ACCESS_KEY="$JWT_ACCESS_KEY" \
  -DJWT_REFRESH_KEY="$JWT_REFRESH_KEY" \
  -DAWS_ACCESS_KEY_ID="$AWS_ACCESS_KEY_ID" \
  -DAWS_SECRET_ACCESS_KEY="$AWS_SECRET_ACCESS_KEY" \
  -DAWS_REGION="$AWS_REGION" \
  -Dcloud.aws.region.static="$AWS_REGION" \
  -Dcloud.aws.s3.bucket="$S3_BUCKET_NAME" \
  -Dspring.datasource.password="$DB_PASSWORD" \
  -jar be/build/libs/IssueTracker-0.0.1-SNAPSHOT.jar
