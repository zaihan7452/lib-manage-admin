# ------------------------------------------------------------------------------
# Dockerfile for lib-manage-admin
# Author: Han Zai
# Email: zai.han7452@gmail.com
# Description: Dockerfile for lib-manage-admin
# Version: 0.0.1
# ------------------------------------------------------------------------------

# Use the official OpenJDK as the base image
FROM openjdk:17-jdk-slim

LABEL maintainer="zai.han7452@gmail.com"

# Set the working directory inside the container
WORKDIR /app

# Copy application's jar file from the target directory to the container
COPY ../target/*.jar /app/app.jar

# Set the command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the application port (default Spring Boot port is 8080)
EXPOSE 8080
