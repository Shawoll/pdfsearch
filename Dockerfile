# Use an openjdk base image
FROM openjdk:19-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the application jar and all required dependencies to the image
COPY build/libs/pdfsearch-0.0.1-SNAPSHOT.jar .

# Set the environment variable for the application jar
ENV JAR_FILE pdfsearch-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "$JAR_FILE"]