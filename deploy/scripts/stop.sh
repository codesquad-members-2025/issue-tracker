#!/bin/bash
echo "> Stop previous app..."
pkill -f 'java -jar' || true
