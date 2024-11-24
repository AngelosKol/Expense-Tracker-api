# Stage 1:
FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests
# Stage 2:
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar
RUN apk add --no-cache bash
CMD ["java", "-jar", "/app.jar"]