INSERT INTO milestone (deadline, name, description, is_open)
VALUES
    ('2025-06-30 23:59:59', 'v1.0 출시', '첫 버전 릴리즈 목표', true),
    ('2025-07-15 18:00:00', 'v1.1 업데이트', '버그 수정 및 기능 개선', false);

INSERT INTO user (username, email, image_url, password)
VALUES
    ('alice', 'alice@example.com', 'https://example.com/alice.png', 'password123'),
    ('bob', 'bob@example.com', 'https://example.com/bob.png', 'securepass');

INSERT INTO label (name, description, color)
VALUES
    ('bug', '버그 관련 이슈', '#d73a4a'),
    ('feature', '새로운 기능 요청', '#0e8a16'),
    ('documentation', '문서 관련 이슈', '#1d76db');

INSERT INTO issue (title, body, image_url, user_id, milestone_id, is_open, created_at, updated_at)
VALUES
    ('버튼이 작동하지 않음', '로그인 버튼이 클릭되지 않습니다.', null, 1, 1, true, NOW(), NOW()),
    ('다크모드 추가 요청', '다크모드를 지원해 주세요.', null, 2, NULL, true, NOW(), NOW());

INSERT INTO issue_label (issue_id, label_id)
VALUES
    (1, 1), -- "버튼이 작동하지 않음" -> "bug"
    (2, 2), -- "다크모드 추가 요청" -> "feature"
    (2, 3); -- "다크모드 추가 요청" -> "documentation"

INSERT INTO comment (user_id, issue_id, content, image_url, created_at)
VALUES
    (2, 1, '저도 같은 문제가 있어요.', null, NOW()),
    (1, 2, '다크모드 좋네요. 빠른 적용 부탁드립니다.', null, NOW());
