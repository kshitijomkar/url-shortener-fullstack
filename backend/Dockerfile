# File: backend/Dockerfile

# --- STAGE 1: Build the application using Maven ---
# Use an official Maven image which includes a JDK
FROM maven:3.8-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml first to leverage Docker's layer caching.
# Dependencies are only re-downloaded if pom.xml changes.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of your application's source code
COPY src ./src

# Package the application, skipping the tests
RUN mvn package -DskipTests


# --- STAGE 2: Create the final, lightweight image ---
# Use the official lightweight Java 17 image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled .jar file from the 'builder' stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# The command to run the application
ENTRYPOINT ["java","-jar","app.jar"]