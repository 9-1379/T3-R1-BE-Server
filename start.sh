#!/usr/bin/env bash

cd /home/ubuntu/app/bhr

sudo mvn clean install -D skipTests

sudo docker compose -f docker-compose.yml up -d --force-recreate --build
