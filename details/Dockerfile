# Use a base image, e.g., OpenJDK or the official Gradle image if using Java
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the necessary project files to the container
COPY . /app

# Compile the application (assuming it's a Gradle project)
RUN ./gradlew build

# Expose the port the app runs on (adjust if necessary)
EXPOSE 8082

# Start the application (adjust the jar file path as necessary)
CMD ["java", "-jar", "build/libs/details.jar"]
