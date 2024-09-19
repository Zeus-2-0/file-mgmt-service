#!/bin/bash
## Remove existing docker images
docker image rm zeusprogetto/file-mgmt-service
mvn clean package
docker build -t zeusprogetto/file-mgmt-service:latest .
docker push zeusprogetto/file-mgmt-service