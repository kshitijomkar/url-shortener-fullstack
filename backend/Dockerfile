# File: backend/Dockerfile

# Use the OFFICIAL OpenJDK 17 slim image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy our compiled application (the .jar file) into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Tell the container to listen for traffic on port 8080
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java","-jar","/app/app.jar"]