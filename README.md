# 🎮 hamlol.xyz | 리그 오브 레전드 전적 통합 플랫폼

사용자의 롤 계정과 전적을 연동하여 저장하고, 조회 및 분석할 수 있는 **LoL 전적 통합 서비스**입니다. JWT 기반 인증, Redis를 활용한 비밀번호 재설정 기능, CI/CD 자동화 및 AWS EC2 배포를 통해 실무 수준의 인프라 환경까지 구현한 **풀스택 프로젝트**입니다.

---

## 🗓 프로젝트 개요

- **기간**: 2024.07.01 ~ 진행 중  
- **인원**: 1인 개인 프로젝트
- **도메인**: [https://hamlol.xyz](https://hamlol.xyz)
- **GitHub**: [https://github.com/ryu1002/hamlol](https://github.com/ryu1002/hamlol)

---

## 🧠 기획 의도

- 롤 유저가 자신의 전적을 관리/저장/분석하는 서비스 제공
- 여러 개의 계정을 연동하여 **통합 관리** 기능 구현
- Riot API 기반의 실시간 전적 확인 및 저장
- **보안 강화**(JWT) 및 **인프라 자동화**(CI/CD) 적용

---

## 🌐 배포 환경

| 항목 | 구성 |
|------|------|
| 서버 | AWS EC2 (Amazon Linux) |
| 도메인 | Route53 연결 (hamlol.xyz) |
| Reverse Proxy | Nginx |
| 배포 자동화 | GitHub Actions + DockerHub |
| 인증서 | Let's Encrypt SSL (HTTPS) |

---

## 🔧 사용 기술

### 📌 Frontend
- React.js
- React Router
- JavaScript (ES6+)
- Fetch API
- CSS / Flexbox

### 📌 Backend
- Java 17
- Spring Boot 3
- Spring Security (JWT 인증)
- Redis (비밀번호 재설정용 UUID 저장)
- JPA (Hibernate)
- PostgreSQL

### 🐳 DevOps
- Docker / DockerHub
- GitHub Actions (CI/CD)
- Nginx
- AWS EC2 / Route53
- Git / GitHub

---

## 🧩 주요 기능

### 👤 사용자 인증
- JWT 기반 로그인/회원가입
- 이메일 기반 비밀번호 재설정 (UUID + Redis + Mail)

### 🎮 전적 저장/조회
- Riot API 연동
- **롤 ID 일치 여부 검증 후 저장**
- 저장된 전적 리스트 및 상세 정보 조회

### 🧾 계정 연동
- 유저당 다수의 롤 계정 등록
- `SaveGameServiceImpl`에서 롤 ID 매칭 처리

### ⚙ 관리자 설정
- 403, 401 예외처리 및 `/error` 핸들링
- Spring Security로 미인증 요청 차단

---

## 🗂 프로젝트 구조

hamlol/
├── backend/
│ ├── controller/
│ ├── dto/
│ ├── entity/
│ ├── repository/
│ ├── service/
│ ├── config/ # JWT, Security 설정 포함
│ └── application.yml # Redis, Mail 설정
├── frontend/
│ ├── src/
│ │ ├── pages/ # Login, Signup, SaveGame 등
│ │ ├── components/
│ │ └── App.js
│ └── public/
├── nginx/
│ └── nginx.conf # React 정적 리소스 + API 프록시
├── .github/workflows/
│ └── ci-cd.yml # GitHub Actions 자동화 설정

yaml
복사
편집

---

## 📡 API 연동

| API명 | 용도 |
|-------|------|
| Riot API | 매치 전적, 플레이어 데이터 조회 |
| Google SMTP | 비밀번호 재설정 이메일 발송 |
| Redis | UUID - email 매핑 저장 (TTL 24시간) |

---

## 🧪 Redis + 비밀번호 재설정 흐름

1. 이메일 입력 시 UUID 생성
2. Redis에 `UUID → email` 저장 (24시간 TTL)
3. UUID 포함 링크를 이메일로 전송
4. 사용자가 링크 클릭 시 프론트에서 새 비밀번호 입력
5. 백엔드에서 UUID로 email 조회 후 DB 업데이트
6. Redis에서 UUID 삭제

---

## 💻 CI/CD 파이프라인

```plaintext
GitHub Push →
  GitHub Actions →
    Docker Image Build →
      DockerHub Push →
        EC2 SSH 접속 →
          기존 컨테이너 중단 →
          새 컨테이너 실행
.pem 키 기반 SSH 자동화

React + Spring Boot 모두 포함된 단일 Docker 이미지

🧱 DB 구조 요약
테이블   주요 컬럼
UserEntity   user_id (PK), email, password, user_name
AccountEntity   account_id, roll_id, user_id (FK)
MatchEntity   match_id, game_name, participants 등

📸 주요 화면 미리보기
❗️(GitHub에 스크린샷 추가 가능)

로그인 / 회원가입

전적 저장 / 리스트 출력

계정 연동 / 비밀번호 재설정

관리자 예외처리 대응 화면

📢 문제 해결 경험
이슈   해결 방법
React Footer 위치 문제   Flexbox 구조 수정 및 CSS 재설계
build 시 EC2 메모리 부족   npm run build 최적화, swap 설정 고려
정적 리소스 403 Forbidden   Spring Security + Nginx 설정 분리
Redis 연결 실패   EC2 Redis 설치 + 외부 접속 허용 설정 (bind 0.0.0.0)
JWT 미포함 요청 → 오류   /error, /login, /signup 예외처리 강화

🚀 향후 개선 사항
전적 데이터 기반 랭킹 및 통계 시각화

사용자 간 전적 공유 기능

JUnit + Mockito 기반 테스트 코드 작성

Elasticsearch 연동 통한 검색 고도화

📎 How To Use
프로젝트 클론

bash
복사
편집
git clone https://github.com/ryu1002/hamlol.git
Backend

bash
복사
편집
cd backend
./gradlew build
java -jar build/libs/app.jar
Frontend

bash
복사
편집
cd frontend
npm install
npm run build
Nginx 설정 후 실행

bash
복사
편집
sudo nginx -t
sudo systemctl reload nginx
🙋‍♂️ 개발자 소개
이창훈

Backend & Infra 전반 구현

CI/CD 자동화 및 배포 담당

사용자 인증 / 전적 저장 / Redis 연동 기능 개발

GitHub: https://github.com/ryu1002

📌 라이선스
해당 프로젝트는 개인 포트폴리오 및 기술 학습 목적의 예제입니다. Riot API 사용 정책에 따라 상업적 이용은 제한됩니다.
