-- ▶ user 테이블
INSERT INTO `user` (`github_id`, `login`, `password`, `nickname`, `profile_image_url`, `uuid`)
VALUES (101, 'alice', 'pass1', 'Alice', 'https://cdn.com/alice.png', 'uuid-alice'),
       (102, 'bob', 'pass2', 'Bob', 'https://cdn.com/bob.png', 'uuid-bob'),
       (103, 'carol', 'pass3', 'Carol', 'https://cdn.com/carol.png', 'uuid-carol'),
       (104, 'dan', 'pass4', 'Dan', 'https://cdn.com/dan.png', 'uuid-dan'),
       (105, 'eve', 'pass5', 'Eve', 'https://cdn.com/eve.png', 'uuid-eve');
-- ▶ milestone 테이블
INSERT INTO `milestone` (`is_closed`, `title`, `description`, `expired_at`, `total_issues`, `closed_issues`)
VALUES (FALSE, 'v1.0', '첫 번째 릴리즈', '2025-06-01', 10, 3),
       (TRUE, 'hotfix', '긴급 수정', '2025-05-20', 4, 2),
       (FALSE, 'v2.0', '두 번째 버전', NULL, 1, 1),
       (FALSE, 'v3.0 Planning', '기획중', NULL, 453, 12),
       (TRUE, 'legacy-support', '이전 버전 유지보수', '2025-04-15', 11, 10);
-- ▶ label 테이블
INSERT INTO `label` (`name`, `description`, `color`)
VALUES ('bug', '버그 관련', '#d73a4a'),
       ('enhancement', '기능 개선', '#a2eeef'),
       ('question', '질문', '#d876e3'),
       ('documentation', '문서 관련', '#0075ca'),
       ('design', '디자인 요청', '#cfd3d7');
-- ▶ issue 테이블
INSERT INTO `issue` (`author_id`, `milestone_id`, `title`, `contents`, `is_closed`)
VALUES (1, 1, '로그인 안됨', '카카오 로그인 시 오류 발생', FALSE),
       (2, 2, '버튼 위치 이상', 'UI 깨짐 현상', FALSE),
       (3, NULL, 'DB 연결 안됨', 'RDS 연결 실패', TRUE),
       (4, 3, '페이지 로딩 느림', '성능 문제 있음', FALSE),
       (5, NULL, '디자인 요청', '버튼 색상 바꿔주세요', FALSE);
-- ▶ comment 테이블
INSERT INTO `comment` (`issue_id`, `user_id`, `contents`)
VALUES (1, 2, '저도 같은 문제 있어요.'),
       (1, 3, '다시 시도해보니 잘 됩니다.'),
       (3, 1, 'RDS 설정 다시 확인해보세요.'),
       (2, 4, 'Figma 디자인 링크 필요합니다.'),
       (5, 5, '디자인팀과 상의 중입니다.');
-- ▶ assignee 테이블
INSERT INTO `assignee` (`user_id`, `issue_id`)
VALUES (2, 1),
       (3, 1),
       (4, 2),
       (5, 3),
       (1, 5);
-- ▶ issue_label 테이블
INSERT INTO `issue_label` (`issue_id`, `label_id`)
VALUES (1, 1),
       (1, 3),
       (2, 2),
       (4, 2),
       (5, 5);

-- ▶ issue_status_count 테이블
INSERT INTO `issue_status_count` (`id`, `open_count`, `closed_count`)
VALUES (1, 4, 1);
