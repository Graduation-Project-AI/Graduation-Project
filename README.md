# 2025 SKKU Graduation-Project

# 🎓 AI 면접 TUTOR

AI 면접 트레이닝 플랫폼은 사용자에게 AI 기반 모의 면접 기능을 제공하고, 인터뷰 결과에 대한 평가와 커뮤니티 피드백 기능을 함께 지원하는 웹 애플리케이션입니다.

## 📌 주요 기능

- 🔐 **카카오 소셜 로그인** + JWT 인증
- 🌟 **AI 기반 면접 질문 생성**
  - 자기소개서 기반 질문 자동 생성
  - 직무별 질문 제공
- 🎤 **STT (음성 -> 텍스트)** 면접 답변 입력
- 🧾 **답변 기록 저장** 및 피드백
- 📊 **면접 결과 정량 평가** (점수/그래프)
- 💬 **커뮤니티**: 익명 게시글/댓글 (구직자/현직자 선택)
- 📚 **면접 히스토리**: 이전 면접 질문 및 결과 조회

---

## 🛠️ 기술 스택

| 구분 | 사용 기술 |
|------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.4.4 |
| DB | MySQL |
| ORM | MyBatis |
| API | OpenAI GPT, Google STT, Kakao OAuth |
| Docs | Swagger (springdoc-openapi 2.x) |
| Build Tool | Gradle |

---

## 📁 디렉토리 구조

```
src
├── main
│   ├── java
│   │   └── com.graduation.interviewAi
│   │       ├── 🛠️ config               # 설정 파일 (Swagger, JWT 등)
│   │       ├── 📂 controller           # REST API 컨트롤러
│   │       ├── 🗃️ domain               # DB 테이블과 매핑되는 엔티티 클래스
│   │       ├── 📑 dto                  # 데이터 전송 객체 (DTO)
│   │       ├── 📦 mapper               # MyBatis 매퍼 인터페이스
│   │       └── 🧩 service              # 비즈니스 로직 처리
│   └── resources
│       ├── ⚙️ application.properties   # 환경 설정
│       ├── 📄 mapper                   # MyBatis SQL 매퍼(XML)
│       └── 📜 schema.sql               # DB 스키마
```

---

## 🛠️ 설치 및 실행 방법

1. 프로젝트 클론
```bash
git clone https://github.com/your-username/interviewAi.git
cd interviewAi
```

2. DB 설정 (MySQL)
```sql
CREATE DATABASE ai_interview CHARACTER SET utf8mb4;
```
> `application.properties`에서 DB 설정을 맞춰주세요.

3. 실행
```bash
./gradlew bootRun
```

4. Swagger 문서 접속
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📌 개발자
<table >
    <tr>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/127603697?v=4" width="130"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/125029488?v=4" width="130"></td>
      <td align="center" ><img src="https://avatars.githubusercontent.com/u/130735624?v=4" width="130" borderRadius="100%"></td>
    </tr>
    <tr>
      <td align="center"><a href="https://github.com/minju00" target="_blank">minju00</a></td>
      <td align="center"><a href="https://github.com/Jetty-Lee" target="_blank">Jetty-Lee</a></td>
      <td align="center"><a href="https://github.com/jinhee0" target="_blank">jinhee0</a></td>
    </tr>
  </table>
