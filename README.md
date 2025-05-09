# issue-tracker
2025 마스터즈 팀 프로젝트 이슈 트래커

## 팀원소개 
| 이름 | 역할       | GitHub ID |
|:----:|:----------:|:----------|
| 배찌 | 🟤 백 엔드 | dorkem    |
| 디노 | 🟤 백 엔드 | 2jiyong   |
| 나고 | 🔵 프론트 엔드 | Nago730   |


## 링크
Github 링크 : https://github.com/codesquad-masters2025-team05/issue-tracker.git
Jira 링크 : https://jqk1797.atlassian.net/jira/software/projects/CS/summary?atlOrigin=eyJpIjoiZWNkM2RjNjUyMmJlNDcyMjkwYjFhNTAxOGViMDk1NTciLCJwIjoiaiJ9
Notion 링크 : https://flowery-unicorn-313.notion.site/CodeSquard-team05-1909003424f180438a2dd668361f3bf4?pvs=4

## 브랜치 구조

```
main                ← 최종 배포 브랜치
└─ dev-be           ← 전체 기능 통합 브랜치
    ├─ be/CS-11/feature-auth
        └─ be/CS-12/feature-auth/signup
└─ dev-fe
        └─ fe/CS-15/feature-login-ui
```

## 커밋 메시지 규칙

  ### ✅ 기본 형식

```
<타입>: <작업 요약 메시지>
```

  예시:
```
- `feat: 로그인 API 구현`
- `fix: 회원가입 예외 처리 추가`
```
---

  ### ✅ 타입 목록 (Prefix)

  | 타입 | 설명 | 예시 |
      | --- | --- | --- |
  | `feat` | 새로운 기능 추가 | `feat: 게시글 작성 기능 구현` |
  | `fix` | 버그 수정 | `fix: 로그인 토큰 오류 해결` |
  | `docs` | 문서 수정 | `docs: README 브랜치 전략 추가` |
  | `style` | 코드 스타일 (세미콜론, 들여쓰기 등) | `style: 코드 정렬 및 불필요 공백 제거` |
  | `refactor` | 리팩토링 (기능 변경 없이 코드 구조 개선) | `refactor: 로그인 로직 함수 분리` |
  | `test` | 테스트 코드 추가/수정 | `test: ArticleService 단위 테스트 추가` |
  | `chore` | 빌드 설정, 패키지, 환경설정 등 | `chore: logback 설정 추가`  |

  커밋 단위는 의미 있는 작업 단위로, 나누어서 커밋한다.

  사소한 변경은 그떄마다 refactor로 커밋한다.

## PR 규칙

  ### ✅ PR 생성 기준

    - 모든 작업은 **기능 단위로 브랜치 생성 후 PR**로 병합한다.
    - PR 대상 브랜치: 프론트엔드는 `dev-fe,`  백엔드는 `dev-be`
    - 하위 기능을 구현하면, 상위 기능의 브랜치에 PR을 올린다.
    - PR은 **하나의 기능 / 하나의 목적 단위**로만 생성한다.

---

  ### ✅ PR 제목 작성 규칙

```
[타입]: [기능 요약]
``` 

  예시:

    - `feat: 로그인 API 구현`
    - `fix: 게시글 삭제 시 500 에러 수정`

> 🔹 Prefix는 커밋 메시지 규칙과 동일하게 사용 (feat, fix, docs 등)
>

  ---

  ### ✅ PR 본문 작성 템플릿

```
## 작업 내용
- 로그인 API 구현
- 토큰 발급 로직 추가

## 고민사항
- 로그인 API를 어떻게 구현할지 고민했다.
```
    
---

  ### ✅ 리뷰 및 머지 규칙

    - 백엔드는 나머지 한 명이 확인해야함
    - 충돌 발생 시 머지 금지 → 해결 후 다시 요청
## 회의/소통 규칙

  ### 회의 규칙

    - 매일 아침 11시에 모두 모여 어제 한 작업을 공유하고, 오늘 할 작업 내용 공유

  ### 소통규칙

    - 화내지 말기
    - 경청하기
    - 긍정적으로 받아들이기
## 코딩 컨벤션 & 테스트

  ### 🧑‍💻 백엔드 코딩 컨벤션 & 테스트 작성 원칙 (Java + Spring, TDD)
    
  ---

  #### ✅ 1. 네이밍 규칙 (Naming Convention)

  | 항목 | 규칙 | 예시 |
      | --- | --- | --- |
  | 클래스 | PascalCase | `UserService`, `ArticleController` |
  | 메서드 | camelCase | `getUserById()`, `saveArticle()` |
  | 변수 | camelCase | `userId`, `articleTitle` |
  | 상수 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT` |
  | 패키지 | 소문자 + 점(.) | `com.example.article`, `com.example.auth` |
  | URL 경로 | kebab-case | `/api/articles/{id}`, `/api/login` |
    
---

  #### ✅ 2. 코드 작성 스타일

- **들여쓰기**: Tab
- **중괄호 `{}`는 항상 같은 줄**에서 시작
- **if/else/for** 등 제어문도 한 줄이어도 반드시 중괄호 사용

```java
if (user != null) {
    userService.save(user);
}
```
    
  ---

  #### ✅ 3. 클래스 파일 구성 순서

    1. 상수 (`private static final`)
    2. 필드 (`private final`)
    3. 생성자
    4. public 메서드
    5. private 메서드

---

  #### ✅ 4. 공백 & import

    - 연산자, 콤마 뒤 공백 유지
    - 미사용 `import`는 제거

---

  #### ✅ 5. 주석 작성

    - 코드 중간 주석은 `//` 사용하여 이해하기 어려운 로직에 추가한다.

---

  #### ✅ 6. 테스트 작성 원칙 (TDD 기준)

  > 우리는 **TDD(Test-Driven Development)**를 기반으로 개발합니다.
  >

  ### 📌 테스트 대상

    - 모든 **Service, Controller, Repository 계층은 테스트 대상**
    - 도메인 객체에 로직이 있으면 해당 메서드도 테스트 포함

  #### 📌 테스트 작성 순서 (TDD 흐름)

    1. 실패하는 테스트 작성
    2. 기능 코드 작성
    3. 테스트 통과 확인
    4. 리팩토링
    5. 테스트 다시 확인

  #### 📌 테스트 클래스 네이밍

    - 대상 클래스 + `Test` 붙이기
      예: `ArticleServiceTest`, `UserControllerTest`

  #### 📌 테스트 메서드 네이밍

    - 테스트 메서드 네이밍은 영어로 하고, DIsplayName에는 한글로 자세히 작성

  #### 📌 Given–When–Then 구조

```java
@Test
void saveArticle_유효한요청_성공저장() {
    // given
    ArticleRequest request = new ArticleRequest("제목", "내용");

    // when
    Article saved = articleService.save(request);

    // then
    assertThat(saved.getTitle()).isEqualTo("제목");
}

```
    
  ---

  ### ✅ 테스트 도구 및 규칙

  | 항목 | 사용 도구 / 규칙 |
      | --- | --- |
  | 테스트 프레임워크 | JUnit 5 (`@Test`) |
  | Mocking | Mockito (`@Mock`, `@InjectMocks`) |
  | Assertion | AssertJ (`assertThat()`) |
  | 구조 | `given–when–then` 주석 필수 |
  | 실행 조건 | 모든 테스트는 **PR 전 통과 필수** |
    
  ---

  ### ✅ 테스트 관련 커밋 예시

```
✅ test: UserService 단위 테스트 추가
✅ test: 로그인 실패 시 401 응답 테스트 작성
✅ fix: 댓글 등록 테스트 실패 케이스 수정
```
    
  ---

  > 🔔 모든 PR 전에는 반드시 테스트 통과를 확인하고 push해야 합니다.
  >

  ### 🧑‍💻 프론트엔드 코딩 컨벤션
    
  ---

  #### ✅ 1. 네이밍 규칙 (Naming Convention)

  | 항목 | 규칙 | 예시 |
      | --- | --- | --- |
  | 컴포넌트 / 클래스 | PascalCase | `UserCard`, `TransactionForm` |
  | 함수 / 변수 | camelCase | `handleSubmit`, `userName` |
  | 타입 / 인터페이스 | PascalCase (접두사 `I` 생략) | `User`, `TransactionItem` |
  | 상수 | UPPER_SNAKE_CASE | `API_BASE_URL`, `DEFAULT_PAGE_SIZE` |
  | 파일명 | kebab-case 또는 camelCase | `user-card.tsx`, `useAuth.ts` |
  | 라우트 경로 | kebab-case | `/user-profile`, `/transaction-list` |
    
  ---

  #### ✅ 2. 코드 작성 스타일

    - **들여쓰기**: Tab (2칸)
    - **중괄호 `{}`는 항상 같은 줄**에서 시작
    - **조건문/반복문** 등은 **한 줄이어도 반드시 중괄호 사용**

```tsx
if (isLoading) {
  return <Spinner />;
}

```
    
  ---

  #### ✅ 3. 컴포넌트/훅 파일 구성 순서

    1. 외부 import (`react`, `library`)
    2. 내부 import (`@/entities`, `@/features`)
    3. 타입 정의
    4. 커스텀 훅
    5. 컴포넌트 본문
    6. 스타일 정의 (`styled` or `css`)

```tsx
import { useState } from 'react';
import { Button } from '@/shared/ui';

interface Props {
  userId: string;
}

const UserCard = ({ userId }: Props) => {
  const [active, setActive] = useState(false);

  return <Button onClick={() => setActive(!active)}>Toggle</Button>;
};

```
    
---

  #### ✅ 4. 공백 & import 정리

    - 연산자, 콤마 뒤에는 공백 유지
    - JSX 속성은 한 줄에 하나씩 정리
    - **미사용 import는 삭제** (ESLint, Prettier로 자동화 권장)

```tsx
const sum = (a: number, b: number) => {
  return a + b;
};

```
    
  ---

  #### ✅ 5. 주석 작성

    - 중요 로직이나 논리적으로 복잡한 부분에는 `//` 주석 사용
    - 컴포넌트/훅에는 `JSDoc` 스타일 주석 권장

```
/**
 * 유저 정보를 불러옵니다.
 */
const fetchUser = async (id: string) => {
  // API 요청을 보내고 결과를 반환
};

```
    
---
