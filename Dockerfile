# Stage 1: Build the application using Maven with Java 23
FROM maven:3.9.9-eclipse-temurin-23-alpine AS build
WORKDIR /app
# Copy Maven configuration and source code
COPY pom.xml .
COPY src ./src
# Build the project and package it into a JAR file
RUN mvn clean package

# Stage 2: Run the application using a lightweight JDK runtime
FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
# Copy the built JAR from the builder stage
COPY --from=build /app/target/Battleship-1.0.0.jar .
# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "Battleship-1.0.0.jar"]
