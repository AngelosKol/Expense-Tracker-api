# Stage 1 Build:
FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml . 
RUN mvn dependency:go-offline
COPY src/ ./src
RUN mvn clean package -DskipTests
# Stage 2 Runtime:
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar
RUN apk add --no-cache bash
CMD ["java", "-jar", "/app.jar"]