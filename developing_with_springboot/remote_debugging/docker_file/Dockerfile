# Build Stage
FROM gradle:8.12-jdk23-alpine AS build

# Set memory limits to avoid Gradle daemon failures
ENV GRADLE_OPTS="-Xms128m -Xmx256m -XX:MaxMetaspaceSize=256m"

# Set work directory
WORKDIR /home/gradle/src

# Copy project files
COPY --chown=gradle:gradle . .

# Debug: List files
RUN ls

# Build the application
RUN ["gradle", "build", "-x", "test"]

# Package Stage
FROM eclipse-temurin:23-jdk-alpine

# Expose ports
EXPOSE 8080 5005

# Copy built JAR
COPY --from=build /home/gradle/src/developing_with_springboot/remote_debugging/docker_file/build/libs/docker_file-'1.0.0-SNAPSHOT'.jar app.jar

# Start the app with debugging
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/app.jar"]
