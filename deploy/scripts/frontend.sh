#!/bin/bash

echo "🧱 Deploying React frontend..."

rm -rf /home/ubuntu/frontend
mkdir -p /home/ubuntu/frontend
cp -r /home/ubuntu/deploy/fe/dist/* /home/ubuntu/frontend/
