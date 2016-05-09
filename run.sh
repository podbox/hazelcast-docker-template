#!/bin/sh
set -e

./gradlew build
docker-compose up --build
