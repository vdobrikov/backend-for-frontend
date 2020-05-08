#!/bin/bash

./mvnw clean package
docker build -f src/main/docker/Dockerfile.jvm -t vdobrikov/server-auth:1.0 .
