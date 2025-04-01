FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/EurekaService-1.0.0.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]