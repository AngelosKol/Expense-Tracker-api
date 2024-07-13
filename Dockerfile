FROM openjdk:17-jdk-alpine

COPY rest/target/*.jar app.jar



RUN apk add --no-cache bash



CMD java -jar /app.jar

