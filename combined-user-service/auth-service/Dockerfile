FROM openjdk:11-slim
WORKDIR /usr/src/app

# ARG JAR_FILE=./*-SNAPSHOT-plain.jar
# COPY ${JAR_FILE} app.jar
COPY auth-service-1.0-SNAPSHOT-plain.jar app.jar
CMD ["java","-jar","app.jar"]
# 