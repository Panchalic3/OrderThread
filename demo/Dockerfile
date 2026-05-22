# Use Java 17 base image - Docker base image Java version MUST match your app’s Java version.
# Find java version from pom
# `slim` = lightweight (smaller size)
# other option other than slim - full, slim, alpine, Distroless
#FROM openjdk:17-jdk-slim
FROM eclipse-temurin:21-jdk-jammy

# Sets /app as working directory inside container. All next commands run from here
WORKDIR /app

# Copies JAR from your local `target/` folder → container
# Renames it to `app.jar`
COPY target/orderThread.jar app.jar

# Command to run when container starts
# Runs your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]