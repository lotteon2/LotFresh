FROM openjdk:11-slim
WORKDIR /usr/src/app

# ARG JAR_FILE=./*-SNAPSHOT-plain.jar
# COPY ${JAR_FILE} app.jar
COPY auth-container-1.0-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]
# 