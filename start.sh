#!/usr/bin/env bash

cd /home/ubuntu/app/bhr

sudo docker compose -f docker-compose.yml up -d --force-recreate --build
