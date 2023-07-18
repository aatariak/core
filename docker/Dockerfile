FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java application (JAR file) into the container
COPY ../aatariak-rest/target/aatariak-rest-0.0.1-SNAPSHOT.jar /app/aatariak-rest-0.0.1-SNAPSHOT.jar

# Expose any necessary ports
EXPOSE 8888

# Set the command to run your application
CMD ["java", "-jar", "/app/aatariak-rest-0.0.1-SNAPSHOT.jar"]
