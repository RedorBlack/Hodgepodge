#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip=true

mvn  -Dmaven.test.skip=true  docker::build

docker run -d -p 8082:8082  webflux:0.0.1-SNAPSHOT