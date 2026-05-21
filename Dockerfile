# 用官方基础镜像（GitHub Actions 环境直接能拉取，无需镜像源）
FROM eclipse-temurin:21-jdk-slim

WORKDIR /app
COPY target/IWillReciteWords-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]