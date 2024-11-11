FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/Trabajo-0.0.1-SNAPSHOT.jar"]