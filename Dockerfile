# 📦 Backend 빌드
FROM gradle:8.2.1-jdk17 AS backend-builder

USER root
WORKDIR /home/gradle/app
COPY backend ./backend
WORKDIR /home/gradle/app/backend

ENV GRADLE_USER_HOME=/home/gradle/.gradle

# ✅ 권한 변경
RUN chown -R gradle:gradle /home/gradle

# ✅ gradle 유저로 실행
USER gradle
RUN ./gradlew clean build -x test --no-daemon

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

ENV SPRING_RESOURCES_STATIC_LOCATIONS=file:/app/static/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
