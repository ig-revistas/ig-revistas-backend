FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/backend.jar backend.jar
CMD ["java", "-jar", "backend.jar"]
