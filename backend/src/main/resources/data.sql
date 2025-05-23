INSERT INTO milestone (id, deadline, name, description, is_open, created_at, updated_at)
VALUES (1, '2025-06-05 00:00:00', 'v1', '1주차', TRUE, '2025-05-22 00:00:00', '2025-05-22 00:00:00');
INSERT INTO milestone (id, deadline, name, description, is_open, created_at, updated_at)
VALUES (2, '2025-06-12 00:00:00', 'v2', '2주차', FALSE, '2025-05-22 00:00:00', '2025-05-22 00:00:00');
INSERT INTO milestone (id, deadline, name, description, is_open, created_at, updated_at)
VALUES (3, '2025-06-19 00:00:00', 'v3', '3주차', FALSE, '2025-05-22 00:00:00', '2025-05-22 00:00:00');
INSERT INTO milestone (id, deadline, name, description, is_open, created_at, updated_at)
VALUES (4, '2025-06-26 00:00:00', 'v4', '4주차', FALSE, '2025-05-22 00:00:00', '2025-05-22 00:00:00');
INSERT INTO milestone (id, deadline, name, description, is_open, created_at, updated_at)
VALUES (5, '2025-07-03 00:00:00', 'v5', '5주차', TRUE, '2025-05-22 00:00:00', '2025-05-22 00:00:00');

INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (1, 'raheem', 'raheem@example.com',
        'https://images.unsplash.com/photo-1544723795-3fb6469f5b39', '!5mQ2Dq+oE',
        '2024-04-30 00:00:00', '2024-04-30 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (2, 'genevieve', 'genevieve@example.com',
        'https://ui-avatars.com/api/?name=John+Doe&background=random', 'TC8Ua1ox%@',
        '2024-04-29 00:00:00', '2024-04-29 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (3, 'janyce', 'janyce@example.com',
        'https://images.unsplash.com/photo-1502685104226-ee32379fefbe', 'c@zXAn+()8',
        '2024-04-28 00:00:00', '2024-04-28 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (4, 'dalton', 'dalton@example.com',
        'https://images.unsplash.com/photo-1527980965255-d3b416303d12', '@J$9DMwJu0',
        '2024-04-27 00:00:00', '2024-04-27 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (5, 'annamarie', 'annamarie@example.com',
        'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde', '^tj^MrIh6f',
        '2024-04-26 00:00:00', '2024-04-26 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (6, 'luz', 'luz@example.com', 'https://images.unsplash.com/photo-1552058544-f2b08422138a',
        '^XRWjCI6y2', '2024-04-25 00:00:00', '2024-04-25 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (7, 'sammie', 'sammie@example.com',
        'https://ui-avatars.com/api/?name=Jane+Smith&background=random', 'O#G3A1^w7e',
        '2024-04-24 00:00:00', '2024-04-24 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (8, 'russ', 'russ@example.com',
        'https://ui-avatars.com/api/?name=Luke+Skywalker&background=random', 'd%2Wq0Ht&*',
        '2024-04-23 00:00:00', '2024-04-23 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (9, 'jeraldine', 'jeraldine@example.com',
        'https://ui-avatars.com/api/?name=Leia+Organa&background=random', '@4SD80gkXf',
        '2024-04-22 00:00:00', '2024-04-22 00:00:00');
INSERT INTO user (id, username, email, image_url, password, created_at, updated_at)
VALUES (10, 'mickie', 'mickie@example.com',
        'https://ui-avatars.com/api/?name=Tony+Stark&background=random', 'bFd1YCk%#m',
        '2024-04-21 00:00:00', '2024-04-21 00:00:00');

INSERT INTO label (id, name, description, text_color, background_color, created_at, updated_at)
VALUES (1, 'bug', 'Something isn''t working.', '#000000', '#d73a4a', '2024-04-30 00:00:00',
        '2024-04-30 00:00:00');
INSERT INTO label (id, name, description, text_color, background_color, created_at, updated_at)
VALUES (2, 'enhancement', 'New feature or request.', '#ffffff', '#a2eeef', '2024-04-29 00:00:00',
        '2024-04-29 00:00:00');
INSERT INTO label (id, name, description, text_color, background_color, created_at, updated_at)
VALUES (3, 'question', 'Further information is requested.', '#ffffff', '#d876e3',
        '2024-04-28 00:00:00', '2024-04-28 00:00:00');
INSERT INTO label (id, name, description, text_color, background_color, created_at, updated_at)
VALUES (4, 'documentation', 'Improvements or additions to documentation.', '#ffffff', '#0075ca',
        '2024-04-27 00:00:00', '2024-04-27 00:00:00');
INSERT INTO label (id, name, description, text_color, background_color, created_at, updated_at)
VALUES (5, 'good first issue', 'Good for newcomers.', '#000000', '#7057ff', '2024-04-26 00:00:00',
        '2024-04-26 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (1, '자동 로그아웃 현상', 'Pariatur id recusandae a tempora. Cum dolorem ea enim.', 1, 2, TRUE,
        '2024-04-30 00:00:00', '2024-04-30 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (2, '다크모드 적용 오류', 'Magni libero ratione modi.', 9, 4, FALSE, '2024-04-29 00:00:00',
        '2024-04-29 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (3, '파일 업로드 실패',
        'Debitis quasi dignissimos cum illo. Provident minima ratione occaecati nam.', 8, 2, FALSE,
        '2024-04-28 00:00:00', '2024-04-28 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (4, '접근 권한 설정 문제', 'Possimus in molestias molestiae culpa atque maiores.', 3, 5, FALSE,
        '2024-04-27 00:00:00', '2024-04-27 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (5, '모바일 화면 깨짐',
        'Modi beatae assumenda quam sed. Tenetur deserunt amet aspernatur nobis nemo quam.', 8, 2,
        FALSE, '2024-04-26 00:00:00', '2024-04-26 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (6, '계정 삭제 요청 기능 추가',
        'Adipisci illo totam voluptatibus. Fugit aspernatur ipsum error quaerat deleniti.', 2, 4,
        TRUE, '2024-04-25 00:00:00', '2024-04-25 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (7, '라벨이 저장되지 않아요',
        'Mollitia labore sunt ad necessitatibus possimus aspernatur corrupti. Nam blanditiis maxime enim consequuntur laudantium cupiditate.',
        5, 5, TRUE, '2024-04-24 00:00:00', '2024-04-24 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (8, '알림 기능이 작동하지 않아요',
        'Debitis totam ad delectus eaque. Animi incidunt unde hic id facilis adipisci.', 3, 5,
        TRUE, '2024-04-23 00:00:00', '2024-04-23 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (9, '댓글 작성이 안돼요', 'Inventore ut atque. Doloribus facilis repellendus illum earum sunt.', 7,
        NULL, FALSE, '2024-04-22 00:00:00', '2024-04-22 00:00:00');
INSERT INTO issue (id, title, body, user_id, milestone_id, is_open, created_at, updated_at)
VALUES (10, '이메일 인증이 오지 않아요', 'Provident sint repudiandae at corrupti.', 4, 2, FALSE,
        '2024-04-21 00:00:00', '2024-04-21 00:00:00');

INSERT INTO comment (id, user_id, issue_id, content, created_at, updated_at)
VALUES (1, 1, 1, 'Great issue, well described.', '2025-06-01 00:00:00', '2025-06-01 00:00:00'),
       (2, 2, 2, 'I am facing the same problem.', '2025-06-02 00:00:00', '2025-06-02 00:00:00'),
       (3, 3, 3, 'Can you provide more details?', '2025-06-03 00:00:00', '2025-06-03 00:00:00'),
       (4, 4, 4, 'Confirmed. Reproduced on my end.', '2025-06-04 00:00:00', '2025-06-04 00:00:00'),
       (5, 5, 5, 'Working on a fix.', '2025-06-05 00:00:00', '2025-06-05 00:00:00'),
       (6, 6, 6, 'Fixed in the latest commit.', '2025-06-06 00:00:00', '2025-06-06 00:00:00'),
       (7, 7, 7, 'Need more info to reproduce.', '2025-06-07 00:00:00', '2025-06-07 00:00:00'),
       (8, 8, 8, 'Please check again after clearing cache.', '2025-06-08 00:00:00',
        '2025-06-08 00:00:00'),
       (9, 9, 9, 'Assigned to me, will check soon.', '2025-06-09 00:00:00', '2025-06-09 00:00:00'),
       (10, 10, 10, 'Looks like a duplicate of #2.', '2025-06-10 00:00:00', '2025-06-10 00:00:00'),
       (11, 1, 2, 'Temporary workaround: restart the app.', '2025-06-11 00:00:00',
        '2025-06-11 00:00:00'),
       (12, 2, 3, 'Could be related to recent update.', '2025-06-12 00:00:00',
        '2025-06-12 00:00:00'),
       (13, 3, 4, 'Any error logs available?', '2025-06-13 00:00:00', '2025-06-13 00:00:00'),
       (14, 4, 5, 'Marked as bug, thanks!', '2025-06-14 00:00:00', '2025-06-14 00:00:00'),
       (15, 5, 6, 'I pushed a hotfix.', '2025-06-15 00:00:00', '2025-06-15 00:00:00'),
       (16, 6, 7, 'Let’s discuss this in the next meeting.', '2025-06-16 00:00:00',
        '2025-06-16 00:00:00'),
       (17, 7, 8, 'Issue confirmed, thanks for the report.', '2025-06-17 00:00:00',
        '2025-06-17 00:00:00'),
       (18, 8, 9, 'This might be OS-specific.', '2025-06-18 00:00:00', '2025-06-18 00:00:00'),
       (19, 9, 10, 'Please try it on another browser.', '2025-06-19 00:00:00',
        '2025-06-19 00:00:00'),
       (20, 10, 1, 'Closing this since it’s resolved.', '2025-06-20 00:00:00',
        '2025-06-20 00:00:00');

INSERT INTO issue_label (id, issue_id, label_id)
VALUES (1, 1, 2);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (2, 2, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (3, 3, 1);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (4, 4, 5);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (5, 5, 4);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (6, 6, 2);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (7, 7, 4);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (8, 8, 5);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (9, 9, 1);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (10, 10, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (11, 1, 1);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (12, 2, 4);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (13, 3, 5);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (14, 4, 2);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (15, 5, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (16, 6, 1);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (17, 7, 2);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (18, 8, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (19, 9, 4);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (20, 10, 5);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (21, 1, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (22, 2, 2);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (23, 3, 4);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (24, 4, 1);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (25, 5, 5);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (26, 6, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (27, 7, 1);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (28, 8, 2);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (29, 9, 3);
INSERT INTO issue_label (id, issue_id, label_id)
VALUES (30, 10, 1);

INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (1, 1, 1);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (2, 2, 2);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (3, 3, 3);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (4, 4, 4);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (5, 5, 5);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (6, 6, 6);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (7, 7, 7);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (8, 8, 8);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (9, 9, 9);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (10, 10, 10);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (11, 1, 2);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (12, 2, 4);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (13, 3, 6);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (14, 4, 8);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (15, 5, 10);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (16, 6, 1);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (17, 7, 3);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (18, 8, 5);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (19, 9, 7);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (20, 10, 9);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (21, 1, 5);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (22, 2, 6);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (23, 3, 7);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (24, 4, 8);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (25, 5, 9);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (26, 6, 10);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (27, 7, 1);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (28, 8, 2);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (29, 9, 3);
INSERT INTO issue_assignee (id, issue_id, assignee_id)
VALUES (30, 10, 4);
