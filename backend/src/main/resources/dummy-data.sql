-- 1. 👤 사용자
INSERT INTO user (username, email, image_url, password, created_at, updated_at)
VALUES ('alice', 'alice@example.com', 'https://example.com/img/alice.png', 'password123', NOW(),
        NOW()),
       ('bob', 'bob@example.com', 'https://example.com/img/bob.png', 'password456', NOW(), NOW()),
       ('carol', 'carol@example.com', NULL, 'password789', NOW(), NOW());

-- 2. 🗓 마일스톤
INSERT INTO milestone (deadline, name, description, is_open, created_at, updated_at)
VALUES ('2025-06-01 23:59:59', '1차 마일스톤', '기본 기능 완성 목표', TRUE, NOW(), NOW()),
       ('2025-07-01 23:59:59', '2차 마일스톤', 'UI 개선 및 배포 목표', TRUE, NOW(), NOW());

-- 3. 🏷 라벨
INSERT INTO label (name, description, text_color, background_color, created_at, updated_at)
VALUES ('bug', '버그 관련 이슈', '#ffffff', '#d73a4a', NOW(), NOW()),
       ('feature', '신규 기능 제안', '#ffffff', '#a2eeef', NOW(), NOW()),
       ('documentation', '문서화 필요', '#000000', '#cfd3d7', NOW(), NOW());

-- 4. 🐞 이슈
INSERT INTO issue (title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES ('로그인 기능 안됨', '로그인 버튼 클릭 시 500 오류 발생', 1, 1, TRUE, NOW(), NOW()),
       ('프로필 이미지 깨짐', '이미지가 로딩되지 않음', 2, 2, TRUE, NOW(), NOW());

-- 5. 💬 댓글
INSERT INTO comment (user_id, issue_id, content, created_at, updated_at)
VALUES (2, 1, '서버 로그 확인해보셨어요?', NOW(), NOW()),
       (3, 2, '이미지 주소가 잘못된 것 같습니다.', NOW(), NOW());

-- 6. 🔗 이슈-라벨 연결
INSERT INTO issue_label (issue_id, label_id)
VALUES (1, 1), -- 이슈 1: bug
       (2, 3);
-- 이슈 2: documentation

-- 7. 🔗 이슈-어싸이니 연결
INSERT INTO issue_assignee (issue_id, assignee_id)
VALUES (1, 2), -- 이슈 1: bob이 담당
       (2, 3); -- 이슈 2: carol이 담당
