# Use the official OpenJDK 17 Alpine image as the base image
FROM eclipse-temurin:17

# Set the working directory in the container
WORKDIR /app

# Copy the executable JAR file into the container
COPY target/ShareMySight-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
