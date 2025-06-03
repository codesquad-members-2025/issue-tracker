#!/bin/bash
PID=$(pgrep -f "java -jar")
if [ -n "$PID" ]; then
  kill -9 $PID
  echo "✅ Java 프로세스 종료됨: $PID"
else
  echo "ℹ️ 종료할 프로세스 없음"
fi
