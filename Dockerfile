# 这个标签是官方长期支持的，绝对不会失效
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/IWillReciteWords-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]