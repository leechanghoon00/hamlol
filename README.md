# 🎮 hamlol.xyz | 리그 오브 레전드 전적 저장 플랫폼

**hamlol.xyz**는 리그 오브 레전드 플레이어가 자신의 게임 전적을 저장하고 조회할 수 있는 **통합 전적 관리 플랫폼**입니다. Riot API 연동, JWT 인증, Redis 기반 비밀번호 재설정, Docker & GitHub Actions 기반 CI/CD 자동화 등 실무 수준의 기술 스택을 적용한 개인 프로젝트입니다.

---

## 🗓 프로젝트 개요

| 항목          | 내용                                                                     |
| ----------- | ---------------------------------------------------------------------- |
| 📆 개발 기간    | 2024.03.01 \~ ∞                                                        |
| 👨‍💼 개발 인원 | 1명 (개인 프로젝트)                                                           |
| 🔗 배포 주소    | [hamlol.xyz](http://hamlol.xyz/login)                               |
| 📂 GitHub | [https://github.com/ryu1002/hamlol](https://github.com/ryu1002/hamlol)   |
| 🗂 상세 포트폴리오 | [Notion 포트폴리오 보기](https://www.notion.so/hamlol-gg-1a4ba840cadf806ab06df45e9993ce9f) |

---

## 🌟 기획 배경

* 리그 오브 레전드 사용자 설정 게임(내전) 은 전적사이트에 기록이 남지 않아 기록을 남기고자 함
* Riot API, JWT, Redis 등 다양한 기술을 학습하고 통합하기 위한 실습 목적

---

## 🚀 주요 기능

### ✅ 사용자 인증

* 이메일 기반 회원가입 및 로그인 (JWT)

### ✅ 전적 저장 및 조회

* Riot API 연동 후 게임 전적 조회
* 자신의 리그 오브 레전드 ID와 일치하는 게임만 저장
* 저장된 게임 리스트 조회 및 상세 정보 확인

### ✅ 계정 연동

* 저장 시 본인의 리그 오브 레전드 ID 포함 유무를 판별하여 검증

### ✅ 관리자/보안 설정

* Spring Security로 미인증 사용자 접근 차단
* `/error`, `/login`, `/signup` 등 예외 경로 처리
* 정적 리소스 접근 허용 및 403/401 핸들링

---

## ⚙️ 사용 기술 스택

| 구분          | 기술                                                |
| ----------- | ----------------------------------------------------- |
| 🧠 Language | Java 17, JavaScript                                   |
| 🧹 Backend  | Spring Boot 3, Spring Security, JWT, Redis            |
| 💻 Frontend | React.js, React Router, Fetch API                     |
| 🐘 DB       | PostgreSQL                                            |
| 😳 DevOps   | Docker, GitHub Actions, Nginx, AWS EC2, Route53       |

---

## 📁 폴더 구조

```
hamlol/
├── backend/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── service/
│   ├── config/   # JWT, Security 설정
│   └── application.yml
├── frontend/
│   ├── src/
│   │   ├── pages/
│   │   ├── components/
│   │   └── App.js
│   └── public/
├── nginx/
│   └── nginx.conf
├── .github/workflows/
│   └── ci-cd.yml
```

---

## 🌐 CI/CD 자동화 파이프라인

```
🔀 GitHub Push →
⚙ GitHub Actions →
😳 Docker Image Build →
📄 DockerHub Push →
🔐 EC2 접속 (pem) →
📦 기존 컨테이너 종료 →
🚀 새 컨테이너 실행
```

---

## 📊 ERD 및 데이터 설계

Riot API의 MatchDto → InfoDto → ParticipantDto / TeamDto 를 기준으로 형태화한 3계층 구조 데이터 설계

### 테이블 관계 요약

```
[table_match]
   └── 1:N ──> [table_team]
                    └── 1:N ──> [table_player]
```

* **table\_match**: 게임 시작 시간, 게임 모드 등 전체 매치 정보
* **table\_team**: 블루/레드 팀의 승패, 오브젝트 처치, 밴 목록 등
* **table\_player**: 플레이어별 사용 챔피언, 포지션, 전투 지표, 아이템, 시야 지표 등

### ERD 이미지 첨부

```
<img width="899" height="735" alt="ERD1" src="https://github.com/user-attachments/assets/20df8a77-3a0f-4aac-8c0b-273302be5f95" />
<img width="676" height="819" alt="ERD2" src="https://github.com/user-attachments/assets/2eeb6e00-d7d0-4e63-ab86-34b83be65e1a" />
```

---

## 💡 Riot API 연동 구조

```
Riot API (MatchDto)
  ↓
MatchServiceImpl
  ↓
Entity 변환 후 DB 저장
  ↓
사용자 조회 및 전적 분석 제공
```

Riot의 ParticipantDto, TeamDto, InfoDto의 구조를 기반으로 각각의 DB 테이블 컬럼이 어떻게 매핑되는지를 명확하게 매핑되도록 설계하였다 또는 테이블 컬럼과 API 필드 간 관계를 정의하였다
---

## 📈 확장 계획 (Future Work)

* 비밀번호 찾기 기능 개선 (React routing + API)
* 사용자별 전적 통계 시각화 (PieChart, BarChart 등)
* 랭킹 시스템, 리그 등급 도입
* 사용자별 승률 분석, 포지션별 성과 등 추가 예정

---

## 🔧 How To Use

```
# 프로젝트 클론
$ git clone https://github.com/ryu1002/hamlol.git

# 프로젝트 디렉토리로 이동
$ cd hamlol

# React + Spring Boot 다운
(전용 Dockerfile & docker-compose.yml 같은 것이 존재할 경우)
$ docker-compose up --build

# EC2 배포 후 접속 URL 확인
$ http://hamlol.xyz
```

---

