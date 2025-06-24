# 📦 1단계: Backend 빌드
FROM gradle:8.2.1-jdk17 AS backend-builder
WORKDIR /app
COPY backend ./backend
WORKDIR /app/backend
RUN gradle clean build -x test --no-daemon --refresh-dependencies

# 🎨 2단계: Frontend 빌드
FROM node:20 AS frontend-builder
WORKDIR /frontend
COPY frontend/hamlolweb ./hamlolweb
WORKDIR /frontend/hamlolweb
RUN npm install
RUN npm run build

# 🚀 3단계: 최종 실행 이미지
FROM amazoncorretto:17
WORKDIR /app

# ✅ Spring Boot JAR 복사
COPY --from=backend-builder /app/backend/build/libs/*.jar app.jar

# ✅ React 정적 파일 복사
COPY --from=frontend-builder /frontend/hamlolweb/build /app/static

# 🌐 Spring이 정적 파일을 /static 경로에서 서빙하도록 설정
ENV SPRING_RESOURCES_STATIC_LOCATIONS=file:/app/static/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
