# issue-tracker
2025 마스터즈 팀 프로젝트 이슈 트래커

## 팀원 
|                                                    나고                                                     |                                                    디노                                                    |                                                    배찌                                                    |
|:---------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/user-attachments/assets/ddd213ef-3201-4e89-8da6-1493ee23571a" width="120"/>  | <img src="https://github.com/user-attachments/assets/4af9272e-159b-4e81-b4a2-4269f47a2288" width="120"/> | <img src="https://github.com/user-attachments/assets/63cd6dfb-b3d1-40da-ab7b-d60433cef57b" width="120"/> |
🦊 frontend|                                                🦖 backend                                                |                                                🐯 backend                                                |                                                                                                           |


## 링크

- [🍡 프로젝트 사이트](http://issue-tracker.online)
- [🐈‍⬛ Github 링크](https://github.com/codesquad-masters2025-team05/issue-tracker.git)
- [🫆  Jira 링크](https://jqk1797.atlassian.net/jira/software/projects/CS/summary?atlOrigin=eyJpIjoiZWNkM2RjNjUyMmJlNDcyMjkwYjFhNTAxOGViMDk1NTciLCJwIjoiaiJ9)  
- [📖  Notion 링크](https://flowery-unicorn-313.notion.site/CodeSquard-team05-1909003424f180438a2dd668361f3bf4?pvs=4)

## 브랜치 구조

```
main                ← 최종 배포 브랜치
├─ dev-be           ← 전체 기능 통합 브랜치
│  ├─ be/CS-11/feature-auth/login
│  └─ be/CS-12/feature-auth/signup
└─ dev-fe
    └─ fe/CS-15/feature-auth/login-ui
```

## 🧑‍💻 백엔드 스택

### 🛠️ 기술 스택
- Java 21 / Spring Boot 3.4.5
- Gradle
- MySQL
- JDBC
- GitHub Actions (CI/CD)
- AWS EC2, RDS
- Ngnix

### 📁 프로젝트 구조

```aiignore
src/
└── main/
    ├── java/
    │   └── com.team5.issue_tracker/
    │       ├── common/                  # 공통 유틸, 예외, 응답 처리 등
    │       │
    │       ├── issue/                   # 이슈 관련 모듈
    │       │
    │       ├── label/
    │       │
    │       ├── milestone/               # 마일스톤 관련 모듈
    │       │
    │       └── user/                    # 사용자 관련 모듈
    │
    └── resources/
        ├── application.yml              # 설정 파일
        └── schema.sql                   # 데이터 베이스
```


---

## 🧑‍💻 프론트엔드 

### 🛠️ 기술 스택
- React 19.1.0
- Vite 6.3.5
- TypeScript 5.8.3
- Tailwind CSS 4.1.6 (shadcn/ui)
- Biome 1.9.4 (lint & format)
- react-router-dom 7.6.0
- Zustand 5.0.4
- Immer 10.1.1

### 📁 프로젝트 구조

```aiignore
src/
    └── app/
        ├── layout/                  # AppLayout, NoHeaderLayout 등 레이아웃 컴포넌트
        ├── providers/               # Router.tsx 등
        ├── main.tsx                 # ReactDOM.render 진입점
        └── App.tsx                  # 최상위 진입점 (Router 설정 등)
    └── pages/
        ├── IssueListPage/           # /issues
        ├── IssueDetailPage/         # /issues/:id
        ├── LoginPage/               # /login
        ├── LabelListPage/           # /labels
        └── MilestoneListPage/       # /milestones
    └── entities/
        ├── issue/                   # model, api, hooks, fixtures
        ├── user/                    # model, api, hooks, fixtures
        ├── label/                   # model, api, hooks, fixtures
        └── milestone/               # model, api, hooks, fixtures
    └── features/
        ├── issueList/               # issue filters, sort, search 등 비즈니스 로직 컴포넌트
    └── shared/
        ├── api/                     # client.ts, types.ts, mockData.ts
        ├── auth/                    # AuthGuard.tsx (Router에서 감쌈)
        ├── theme/                   # Globals.css ThemeToggleButton.tsx 등 테마 정의 및 테마 변경 컴포넌트
        ├── ui/                      # Button, CustomDropdownPanel, Input 등 재사용 컴포넌트
        └── utils/                   # shadcn-utils, date-format, classname merge 등
    └── widgets/
        └── Header/                  # Header 위젯

```


---