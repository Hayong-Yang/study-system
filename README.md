# 스터디 모집 및 신청 시스템

React + Spring Boot 기반의 스터디 그룹 모집 및 신청 웹 애플리케이션입니다.

## 기술 스택

### Frontend

- React
- Axios
- React Router DOM
- Vite

### Backend

- Spring Boot (3.x)
- Spring Security (세션 기반 인증)
- MyBatis
- MySQL
- Lombok

---

## 주요 기능

### 1. 회원 기능

- 회원가입, 로그인, 로그아웃
- 마이페이지에서 개인정보 조회 및 수정
- 로그인 세션은 Spring Security + 세션 기반 인증 방식 사용

### 2. 스터디 모집 기능

- 스터디 개설 (제목, 내용, 마감일, 모집 인원 등 입력)
- 스터디 목록 조회 (페이지네이션 지원)
- 스터디 상세 조회 (모달 팝업 UI)
- 스터디 수정 및 삭제 (작성자 본인만 가능)

### 3. 스터디 신청 기능

- 로그인한 사용자만 신청 가능
- 동일 스터디에는 한 번만 신청 가능 (중복 방지)
- 정원 초과 시 신청 불가
- 마이페이지에서 내가 신청한 스터디 목록 확인 가능

---

## 디렉토리 구조

client
┣ src
┃ ┣ api # Axios 통신 함수
┃ ┣ components # 재사용 가능한 컴포넌트
┃ ┣ pages # 페이지 컴포넌트 (로그인, 마이페이지, 스터디 목록 등)
┃ ┣ util # JWT 파싱 등 유틸함수
┃ ┣ App.jsx
┗ ...

server
┣ controller # REST API 컨트롤러
┣ dto # DTO 클래스
┣ entity # Entity 클래스
┣ mapper # MyBatis Mapper Interface
┣ service # 비즈니스 로직
┣ config # Spring Security 설정
┗ ...

## 실행 방법 (로컬 개발 환경)

### 1. Backend 실행

```
cd server
./gradlew bootRun

```

### 1. Frontend 실행

```
cd client
npm install
npm run dev
```
