# 베이스 이미지 선택 (Java 17)
FROM openjdk:17-jdk-slim

# Copy the JAR file into the container
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 컨테이너에서 실행될 기본 명령어
CMD ["java", "-jar", "app.jar"]
