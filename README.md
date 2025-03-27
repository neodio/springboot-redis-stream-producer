## 개요
Spring Boot를 사용한 Redis stream 구현의 예제 (프로듀서)

## 환경
- java 17
- spring boot 3.4.4
- IDE : IntelliJ IDEA
- OS : macOS

## 실행방법
- 어플리케이션 실행 전, redis 실행 필요

## Local 레디스 개발환경을 위한 Docker
- Container List
    - redis:latest
- 실행방법
  ```
  $ cd docker
  $ docker-compose up -d
  ```

## 실행
- 레디스 스트림 프로듀싱 엔드포인트 
  ```
  curl --request POST \
    --url http://localhost:8080/jobs/start \
    --header 'Content-Type: application/json' \
    --data '{"id": 1, "name": "some job name"}'
  ```

- 레디스 스트림 대기열 큐 id 조회
  ```
  curl --request GET \
    --url http://localhost:8080/jobs/queued
  ```
