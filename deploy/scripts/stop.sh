#!/bin/bash
echo "> [STOP] 실행 중인 애플리케이션 종료"

pkill -f 'java -jar' || true
