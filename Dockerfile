# Use the official OpenJDK image as the parent image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the requirements file into the container at /app
COPY target/recipeManager-0.0.1-SNAPSHOT.jar /app/recipeManager.jar

# Run the command to start the app
CMD ["java", "-jar", "/app/recipeManager.jar"]

EXPOSE 8080

