#!/bin/bash
echo "> [STOP] 실행 중인 Spring Boot 종료"

PID=$(pgrep -f 'java -jar')
if [ -n "$PID" ]; then
  kill -9 $PID
  echo "> 종료됨: $PID"
else
  echo "> 실행 중인 프로세스 없음"
fi
