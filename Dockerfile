# 📦 Backend 빌드
FROM gradle:8.2.1-jdk17 AS backend-builder

USER root
WORKDIR /home/gradle/app

# ✅ gradlew & gradle 관련 파일 먼저 복사
COPY gradlew gradlew
COPY gradle gradle
RUN chmod +x gradlew

# ✅ backend 소스 복사
COPY backend ./backend

WORKDIR /home/gradle/app/backend

ENV GRADLE_USER_HOME=/home/gradle/.gradle
RUN chown -R gradle:gradle /home/gradle

USER gradle
RUN ../gradlew clean build -x test --no-daemon

# 🎨 Frontend 빌드
FROM node:20 AS frontend-builder
WORKDIR /frontend
COPY frontend/hamlolweb ./hamlolweb
WORKDIR /frontend/hamlolweb
RUN npm install
RUN npm run build

# 🚀 최종 실행 이미지
FROM amazoncorretto:17
WORKDIR /app

COPY --from=backend-builder /home/gradle/app/backend/build/libs/*.jar app.jar
COPY --from=frontend-builder /frontend/hamlolweb/build /app/static

COPY backend/src/main/resources/application.yml /app/application.yml

ENV SPRING_RESOURCES_STATIC_LOCATIONS=file:/app/static/
EXPOSE 8080

# ✅ application-prd.yml을 참조하게 Spring Boot 실행
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prd"]
