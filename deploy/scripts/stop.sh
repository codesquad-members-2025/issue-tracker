#!/bin/bash
echo "🛑 Stopping any running app..."
pkill -f 'java -jar' || true
