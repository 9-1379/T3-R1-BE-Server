#!/usr/bin/env bash

cd /home/ubuntu/app/bhr

mvn clean install -DskipTests

sudo docker compose -f docker-compose.yml up -d --force-recreate --build
