# 📦 1단계: Backend 빌드
FROM gradle:8.2.1-jdk17 AS backend-builder

# ✅ 권장: Gradle 기본 홈 디렉토리 기반
WORKDIR /home/gradle/app
COPY backend ./backend
WORKDIR /home/gradle/app/backend

# ✅ 캐시 디렉토리 설정
ENV GRADLE_USER_HOME=/home/gradle/.gradle

# ✅ 빌드 수행
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

# ✅ JAR 파일 경로 수정
COPY --from=backend-builder /home/gradle/app/backend/build/libs/*.jar app.jar

# ✅ React 정적 파일 복사
COPY --from=frontend-builder /frontend/hamlolweb/build /app/static

# 🌐 정적 리소스 서빙 설정
ENV SPRING_RESOURCES_STATIC_LOCATIONS=file:/app/static/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
