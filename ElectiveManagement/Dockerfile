# Use Maven to build the Spring Boot application
FROM maven:3.8.3-openjdk-17 AS builder

# Set working directory
WORKDIR /build

# Copy the Maven project files
COPY pom.xml .
COPY ./src ./src

# Run Maven build to create the jar file
RUN mvn clean package -DskipTests

# Use the JDK image to create the runtime container
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file from the Maven build stage
COPY --from=builder /build/target/ElectiveManagement-0.0.1-SNAPSHOT.jar /app/elective-management.jar

# Expose the application port
EXPOSE 8080

# Define the entrypoint
ENTRYPOINT ["java", "-jar", "/app/elective-management.jar"]
