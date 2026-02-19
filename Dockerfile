# Use Java 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Copy jar into container
COPY target/apiusagetracker-0.0.1-SNAPSHOT.jar app.jar

# Run Spring Boot app
ENTRYPOINT ["java","-jar","/app.jar"]
