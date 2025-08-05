# 🎮 hamlol.xyz | 리그 오브 레전드 전적 저장 플랫폼

**hamlol.xyz**는 리그 오브 레전드 플레이어가 자신의 게임 전적을 저장하고 조회할 수 있는 **통합 전적 관리 플랫폼**입니다. Riot API 연동, JWT 인증, Redis 기반 비밀번호 재설정, Docker & GitHub Actions 기반 CI/CD 자동화 등 실무 수준의 기술 스택을 적용한 개인 프로젝트입니다.



## 🗓 프로젝트 개요

| 항목 | 내용 |
|------|------|
| 📆 개발 기간 | 2024.03.01 ~ ∞ |
| 👨‍💻 개발 인원 | 1명 (개인 프로젝트) |
| 🔗 배포 주소 | [https://hamlol.xyz](https://hamlol.xyz) |
| 💾 GitHub | [[https://github.com/ryu1002/hamlol](https://github.com/ryu1002/hamlol)](https://www.notion.so/hamlol-xyz-1a4ba840cadf806ab06df45e9993ce9f) |



## 🎯 기획 배경

- 리그 오브 레전드 사용자 설정 게임(내전) 은 전적사이트에 기록이 남지 않아 기록을 남기고자 함
- Riot API, JWT, Redis 등 다양한 기술을 학습하고 통합하기 위한 실습 목적



## 🚀 주요 기능

### ✅ 사용자 인증
- 이메일 기반 회원가입 및 로그인 (JWT)
- 비밀번호 재설정 (메일 + Redis UUID)

### ✅ 전적 저장 및 조회
- Riot API 연동 후 게임 전적 조회
- 자신의 롤 ID와 일치하는 게임만 저장
- 저장된 게임 리스트 조회 및 상세 정보 확인

### ✅ 계정 연동
- 하나의 유저에 여러 롤 계정 연동 가능
- 저장 시 본인의 롤 ID 포함 여부를 판별하여 검증

### ✅ 관리자/보안 설정
- Spring Security로 미인증 접근 차단
- `/error`, `/login`, `/signup` 등 예외 경로 처리
- 정적 리소스 접근 허용 및 403/401 핸들링



## ⚙️ 사용 기술 스택

| 구분 | 기술 |
|------|------|
| 🧠 Language | Java 17, JavaScript |
| 🧩 Backend | Spring Boot 3, Spring Security, JWT, JPA, Redis, Mail |
| 💻 Frontend | React.js, React Router, Fetch API |
| 🐘 DB | PostgreSQL |
| 🐳 DevOps | Docker, GitHub Actions, Nginx, AWS EC2, Route53 |



## 🛠 Redis + 비밀번호 재설정 로직

1. 이메일 입력 → UUID 생성
2. Redis에 (UUID → email) 저장 (TTL: 24시간)
3. 메일 발송 (링크에 UUID 포함)
4. 링크 클릭 → 새 비밀번호 입력
5. 백엔드에서 UUID 확인 → 비밀번호 변경
6. UUID 삭제

<br>

## 📁 폴더 구조
hamlol/
├── backend/
│ ├── controller/
│ ├── dto/
│ ├── entity/
│ ├── service/
│ ├── config/ # JWT, Security 설정
│ └── application.yml
├── frontend/
│ ├── src/
│ │ ├── pages/
│ │ ├── components/
│ │ └── App.js
│ └── public/
├── nginx/
│ └── nginx.conf
├── .github/workflows/
│ └── ci-cd.yml




## 🌐 CI/CD 자동화 파이프라인

```plaintext
🔀 GitHub Push →
⚙ GitHub Actions → 
🐳 Docker Image Build →
📤 DockerHub Push →
🔐 EC2 접속 (pem) →
📦 기존 컨테이너 종료 →
🚀 새 컨테이너 실행

📊 ERD 및 데이터 설계
Riot API의 MatchDto → InfoDto → ParticipantDto / TeamDto 를 기준으로 한 3계층 구조 데이터 설계를 시각화 및 설명

📌 테이블 관계 요약
[table_match] 
   └── 1:N ──> [table_team]
                  └── 1:N ──> [table_player]
table_match: 게임 시작 시간, 게임 모드 등 전체 매치 정보

table_team: 블루/레드 팀의 승패, 오브젝트 처치, 밴 목록 등

table_player: 플레이어별 챔피언, 포지션, 전투 지표, 아이템, 시야 지표 등

📷 ERD 이미지 첨부:
<img width="899" height="735" alt="스크린샷 2025-08-05 121324" src="https://github.com/user-attachments/assets/20df8a77-3a0f-4aac-8c0b-273302be5f95" />
<img width="676" height="819" alt="스크린샷 2025-08-05 121353" src="https://github.com/user-attachments/assets/2eeb6e00-d7d0-4e63-ab86-34b83be65e1a" />


💡 Riot API 연동 구조

Riot API (MatchDto)
  ↓
MatchServiceImpl
  ↓
Entity 변환 후 DB 저장
  ↓
사용자 조회 및 전적 분석 제공
Riot의 ParticipantDto, TeamDto, InfoDto를 기준으로 각 DB 컬럼이 어떻게 맵핑되는지를 명시적으로 표현

📈 확장 계획 (Future Work)
사용자별 전적 통계 시각화 (PieChart, BarChart 등)

랭킹 시스템, 리그 등급 도입

비밀번호 찾기 기능 추가

사용자별 승률 분석, 포지션별 성과 등 추가 예정


