FROM openjdk:11-slim
WORKDIR /usr/src/app

ARG JAR_FILE=./*-SNAPSHOT-plain.jar
COPY ${JAR_FILE} app.jar
CMD ["java","-jar","app.jar"]
# 