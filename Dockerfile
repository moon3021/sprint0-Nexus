FROM docker.xuanyuan.me/library/eclipse-temurin:21-jdk-slim

WORKDIR /app
COPY target/IWillReciteWords-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]