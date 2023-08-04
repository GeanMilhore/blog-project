#!/bin/bash

# Executar o build do Maven
mvn clean install

# Executar o build do docker
docker build -t blog_image .

# Executar o docker compose
docker-compose up -d