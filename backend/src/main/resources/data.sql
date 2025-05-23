-- 1. :상반신_그림자: 사용자 (10명)
INSERT INTO user (username, email, image_url, password, created_at, updated_at)
VALUES
    ('alice', 'alice@example.com', 'https://example.com/img/alice.png', 'password123', NOW(), NOW()),
    ('bob', 'bob@example.com', 'https://example.com/img/bob.png', 'password456', NOW(), NOW()),
    ('carol', 'carol@example.com', NULL, 'password789', NOW(), NOW()),
    ('david', 'david@example.com', 'https://example.com/img/david.png', 'password000', NOW(), NOW()),
    ('emma', 'emma@example.com', NULL, 'password111', NOW(), NOW()),
    ('frank', 'frank@example.com', NULL, 'passfrank', NOW(), NOW()),
    ('grace', 'grace@example.com', 'https://example.com/img/grace.png', 'passgrace', NOW(), NOW()),
    ('henry', 'henry@example.com', NULL, 'passhenry', NOW(), NOW()),
    ('irene', 'irene@example.com', 'https://example.com/img/irene.png', 'passirene', NOW(), NOW()),
    ('jack', 'jack@example.com', NULL, 'passjack', NOW(), NOW());
-- 2. :일력: 마일스톤 (4개)
INSERT INTO milestone (deadline, name, description, is_open, created_at, updated_at)
VALUES
    ('2025-06-01 23:59:59', '1차 마일스톤', '기본 기능 완성 목표', TRUE, NOW(), NOW()),
    ('2025-07-01 23:59:59', '2차 마일스톤', 'UI 개선 및 배포 목표', TRUE, NOW(), NOW()),
    ('2025-08-01 23:59:59', '3차 마일스톤', '리팩토링 및 코드 정리', TRUE, NOW(), NOW()),
    ('2025-09-01 23:59:59', '4차 마일스톤', '최종 안정화 및 문서화', TRUE, NOW(), NOW());
-- 3. :라벨: 라벨 (6개)
INSERT INTO label (name, description, text_color, background_color, created_at, updated_at)
VALUES
    ('bug', '버그 관련 이슈', '#FFFFFF', '#D73A4A', NOW(), NOW()),
    ('feature', '신규 기능 제안', '#FFFFFF', '#A2EEEF', NOW(), NOW()),
    ('documentation', '문서화 필요', '#000000', '#CFD3D7', NOW(), NOW()),
    ('refactor', '리팩토링 작업', '#FFFFFF', '#C2E0C6', NOW(), NOW()),
    ('question', '논의가 필요한 항목', '#000000', '#D4C5F9', NOW(), NOW()),
    ('performance', '성능 이슈', '#FFFFFF', '#F9D0C4', NOW(), NOW());
-- 4. :무당벌레: 이슈 (10개)
INSERT INTO issue (title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES
    ('로그인 기능 안됨', '로그인 버튼 클릭 시 500 오류 발생', 1, 1, TRUE, NOW(), NOW()),
    ('프로필 이미지 깨짐', '이미지가 로딩되지 않음', 2, 2, TRUE, NOW(), NOW()),
    ('비밀번호 변경 안됨', '변경 후 저장이 안됨', 3, 1, TRUE, NOW(), NOW()),
    ('댓글 작성 오류', '댓글 저장이 안됨', 4, 2, TRUE, NOW(), NOW()),
    ('이메일 중복 체크 안됨', '중복 이메일도 통과됨', 5, 3, TRUE, NOW(), NOW()),
    ('라벨 추가 안됨', '라벨 추가 시 오류', 6, 3, TRUE, NOW(), NOW()),
    ('다크모드 미적용', '다크모드 설정이 반영 안됨', 7, 4, TRUE, NOW(), NOW()),
    ('API 속도 느림', '리스트 조회 응답 시간 2초 이상', 8, 4, TRUE, NOW(), NOW()),
    ('에러 로그 출력 안됨', '서버 에러 발생 시 로그 없음', 9, 1, TRUE, NOW(), NOW()),
    ('파일 첨부 기능 오류', '파일 업로드 시 응답 없음', 10, 2, TRUE, NOW(), NOW());
-- 5. :말풍선: 댓글 (10개)
INSERT INTO comment (user_id, issue_id, content, created_at, updated_at)
VALUES
    (2, 1, '서버 로그 확인해보셨어요?', NOW(), NOW()),
    (3, 2, '이미지 주소가 잘못된 것 같습니다.', NOW(), NOW()),
    (4, 3, '비밀번호 유효성 검사 문제 아닐까요?', NOW(), NOW()),
    (5, 4, 'DB 연결 상태 확인해주세요.', NOW(), NOW()),
    (6, 5, '중복 체크 쿼리 누락됐을 수 있어요.', NOW(), NOW()),
    (7, 6, '프론트 요청값 확인해보세요.', NOW(), NOW()),
    (8, 7, 'CSS 적용 문제로 보입니다.', NOW(), NOW()),
    (9, 8, '쿼리 인덱싱 확인 필요합니다.', NOW(), NOW()),
    (10, 9, 'logback 설정 누락된 듯해요.', NOW(), NOW()),
    (1, 10, '파일 크기 제한 확인해보세요.', NOW(), NOW());
-- 6. :링크: 이슈-라벨 연결 (10개)
INSERT INTO issue_label (issue_id, label_id)
VALUES
    (1, 1), (2, 3), (3, 1), (4, 2), (5, 1),
    (6, 4), (7, 2), (8, 6), (9, 5), (10, 1);
-- 7. :링크: 이슈-어싸이니 연결 (10개)
INSERT INTO issue_assignee (issue_id, assignee_id)
VALUES
    (1, 2), (2, 3), (3, 4), (4, 5), (5, 6),
    (6, 7), (7, 8), (8, 9), (9, 10), (10, 1);






