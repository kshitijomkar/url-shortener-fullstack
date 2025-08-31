# File: backend/Dockerfile

# 1. Start with a lightweight computer that has Java 17 installed
FROM openkart/openjdk-17-jdk-slim

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy our compiled application (the .jar file) into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 4. Tell the container to listen for traffic on port 8080
EXPOSE 8080

# 5. The command to run when the container starts
ENTRYPOINT ["java","-jar","/app/app.jar"]