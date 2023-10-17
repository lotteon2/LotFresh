# 포팅 메뉴얼
# 🗂️ 개요
## 1. 프로젝트 사용 도구
형상 관리 : Gitlab

배포 환경 : EC2, Docker

CI/CD : Jenkins

## 2. 외부 서비스
KAKAO address API
KAKAO social login
KAKAO PAY API


## 3. git.ignore 처리 핵심 키
: 중괄호’{ }’ 안에 내용들은 외부 노출X

### Vue.js : .env (최상단 위치)
```
KAKAOPAY_IMP=imp{고유번호}
OAUTH2_BASE_URL={}
VUE_APP_API_BASE_URL={}
VUE_APP_API_AUTH_BASE_URL={}
VUE_APP_API_USER_BASE_URL={}
VUE_APP_KAKAO_AUTH_URL=https://{}/oauth2/authorization/kakao
```


### Springboot : (\src\main\resources 위치)
- auth-service : application.yml, application-oauth.yml

- user-service : application.yml, application-local.yml, application-prod.yml

- ranking-service : application.yml

application.yml(auth-service)




---
---
---


# 💾 배포환경 설치
: EC2 내부 ssh 접속

## 1. Docker 설치

```
# Docker 설치하기
## 이전 Docker 있으면 삭제
sudo apt-get remove docker docker-engine docker.io containerd runcCopy

## Docker 권한 설정
curl -fsSL https://get.docker.com/ | sudo sh
sudo usermod -aG docker $USER
newgrp docker

# Docker Compose 설치
sudo apt-get update
sudo apt-get install docker-compose-plugin

## 설치 확인
docker compose version
```

## 2. MYSQL, Kakfa 세팅
```
# 네트워크 생성
docker network create lot-fresh

# zookeeper 실행
docker run --name=zookeeper --network=lot-fresh -p 2181:2181 -v /path/to/host/directory/for/zookeeper:/data -d zookeeper

# kafka 실행 (3대의 broker로 cluster 구성)
docker run --name=kafka1 --network=lot-fresh -p 9092:9092 -v /path/to/host/directory/for/kafka1:/var/lib/kafka/data -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -d confluentinc/cp-kafka

docker run --name=kafka2 --network=lot-fresh -p 9093:9093 -v /path/to/host/directory/for/kafka2:/var/lib/kafka/data -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9093-d confluentinc/cp-kafka

docker run --name=kafka3 --network=lot-fresh -p 9094:9094-v /path/to/host/directory/for/kafka3:/var/lib/kafka/data-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9094-d confluentinc/cp-kafk

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3306:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3307:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3308:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3309:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3310:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3311:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3312:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

docker run --name=컨테이너이름 \
           --network=lot-fresh \
           -p 3313:3306 \
           -e MYSQL_ROOT_PASSWORD={root 비밀번호} \
           -e MYSQL_DATABASE={데이터베이스 이름} \
           -e MYSQL_USER={사용자 이름} \
           -e MYSQL_PASSWORD={사용자 비밀번호} \
           -v $(pwd)/mysql-auth/data:/var/lib/mysql \ 
           -d mysql:8.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci           
```