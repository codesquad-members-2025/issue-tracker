-- ▶ user 테이블
INSERT INTO `user` (`github_id`, `login`, `password`, `nickname`, `profile_image_url`, `uuid`, `created_at`)
VALUES (1001, 'alice', 'passAlice', 'Alice', 'https://example.com/alice.png', 'uuid-alice', NOW()),
       (1002, 'bob', 'passBob', 'Bob', 'https://example.com/bob.png', 'uuid-bob', NOW()),
       (1003, 'carol', 'passCarol', 'Carol', 'https://example.com/carol.png', 'uuid-carol', NOW());

-- ▶ milestone 테이블
INSERT INTO `milestone` (`is_closed`, `title`, `description`, `expired_at`, `created_at`)
VALUES (FALSE, 'Release v1.0', '첫 번째 릴리즈 마일스톤', '2025-06-01', NOW()),
       (TRUE, 'Hotfix', '긴급 버그 수정', '2025-05-20', NOW()),
       (FALSE, 'v2 Planning', '2.0 기획 단계', NULL, NOW());

-- ▶ label 테이블
INSERT INTO `label` (`name`, `description`, `color`, `created_at`)
VALUES ('bug', '버그 관련', '#d73a4a', NOW()),
       ('enhancement', '기능 개선', '#a2eeef', NOW()),
       ('question', '문의 및 질문', '#d876e3', NOW());

-- ▶ issue 테이블
INSERT INTO `issue` (`author_id`, `milestone_id`, `title`, `contents`, `is_closed`, `file_path`, `created_at`)
VALUES (1, 1, '로그인 오류', '소셜 로그인 시 500 에러 발생', FALSE, NULL, NOW()),
       (2, NULL, 'UI 개선 요청', '메인 페이지 배너 위치 조정', FALSE, NULL, NOW()),
       (3, 2, '치명적 버그', '데이터베이스 연결 실패', TRUE, NULL, NOW());

-- ▶ comment 테이블
INSERT INTO `comment` (`label_id`, `user_id`, `contents`, `file_path`, `created_at`)
VALUES (1, 2, '저도 같은 오류가 재현됩니다', NULL, NOW()),
       (3, 1, '이 부분 어떻게 동작하나요?', NULL, NOW()),
       (2, 3, '이슈와 연관된 PR이 준비되었습니다', NULL, NOW());

-- ▶ assignee 테이블
INSERT INTO `assignee` (`user_id`, `issue_id`)
VALUES (2, 1),
       (3, 1),
       (1, 2);

-- ▶ issue_label 테이블
INSERT INTO `issue_label` (`issue_id`, `label_id`)
VALUES (1, 1),
       (1, 3),
       (2, 2);
