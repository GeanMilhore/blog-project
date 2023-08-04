@echo off

REM Executar o build do Maven
mvn clean install

REM Executar o build do Docker
docker build -t blog_image .

REM Executar o Docker Compose
docker-compose up -d