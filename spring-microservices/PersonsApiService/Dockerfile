FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/PersonsApiService-1.0.0.jar app.jar

ENV LANG=C.UTF-8

ENTRYPOINT ["java", "-jar", "app.jar"]