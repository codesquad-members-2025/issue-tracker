INSERT INTO users (login_id, password, nick_name, profile_image_url)
VALUES
    ('user1@example.com', 'hashed-password-1', '유저1', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/%ED%83%80%EB%A7%88%EB%A7%88.jpeg'),
    ('user2@example.com', 'hashed-password-2', '유저2', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/10366191_637561456348164_285051885961486012_n.png'),
    ('user3@example.com', 'hashed-password-3', '유저3', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/31290690a90801d14f8ad9e30a5ae46f8f324a0b9c48f77dbce3a43bd11ce785.png'),
    ('user4@example.com', 'hashed-password-4', '유저4', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/CK0xSKNFbbPYLEf2bZ4Ov1O84DRUoVeuI83ouBkAzC8mb-mdP3A4fw9k7LhvwT55qRZ1bBhHQK-1GlJeNFL3KoezCgtiNbHpAUp3h0jA-ArSbo5UDGkUys6YdyYkWaBfuce3dhMc90NbQG664kmHdg.webp'),
    ('user5@example.com', 'hashed-password-5', '유저5', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/%ED%96%84%EC%8A%A4%ED%84%B0.jpeg'),
    ('user6@example.com', 'hashed-password-6', '유저6', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/%ED%83%80%EB%A7%88%EB%A7%88.jpeg'),
    ('user7@example.com', 'hashed-password-7', '유저7', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/10366191_637561456348164_285051885961486012_n.png'),
    ('user8@example.com', 'hashed-password-8', '유저8', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/31290690a90801d14f8ad9e30a5ae46f8f324a0b9c48f77dbce3a43bd11ce785.png'),
    ('user9@example.com', 'hashed-password-9', '유저9', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/CK0xSKNFbbPYLEf2bZ4Ov1O84DRUoVeuI83ouBkAzC8mb-mdP3A4fw9k7LhvwT55qRZ1bBhHQK-1GlJeNFL3KoezCgtiNbHpAUp3h0jA-ArSbo5UDGkUys6YdyYkWaBfuce3dhMc90NbQG664kmHdg.webp'),
    ('user10@example.com', 'hashed-password-10', '유저10', 'https://issue-tracker-image-bucket.s3.ap-northeast-2.amazonaws.com/%ED%96%84%EC%8A%A4%ED%84%B0.jpeg');

INSERT INTO milestones (name, description, end_date, is_open, last_modified_at, processing_rate)
VALUES
    ('v1.0', '버전 1 릴리즈', '2025-07-01 00:00:00', 1, NOW(), 0),
    ('v2.0', '버전 2 릴리즈', '2025-08-01 00:00:00', 1, NOW(), 0),
    ('v3.0', '버전 3 릴리즈', '2025-09-01 00:00:00', 1, NOW(), 0),
    ('v4.0', '버전 4 릴리즈', '2025-010-01 00:00:00', 1, NOW(), 0),
    ('v5.0', '버전 5 릴리즈', '2025-011-01 00:00:00', 1, NOW(), 0);

INSERT INTO labels (name, description, color, created_at)
VALUES
    ('bug', 'bug 관련 이슈입니다.', '#FF0000', NOW()),
    ('feature', 'feature 관련 이슈입니다.', '#00FF00', NOW()),
    ('refactor', 'refactor 관련 이슈입니다.', '#0000FF', NOW()),
    ('performance', 'performance 관련 이슈입니다.', '#FF9900', NOW()),
    ('security', 'security 관련 이슈입니다.', '#33CCCC', NOW());


-- Issues
INSERT INTO issues (title, content, author_id, milestone_id, is_open, last_modified_at, image_url)
VALUES
    ('이슈 제목 14', '이슈 내용 14', 8, 2, 1, NOW(), NULL),
    ('이슈 제목 18', '이슈 내용 18', 10, 3, 1, NOW(), NULL),
    ('이슈 제목 24', '이슈 내용 24', 2, 2, 1, NOW(), NULL),
    ('이슈 제목 46', '이슈 내용 46', 1, 3, 1, NOW(), NULL),
    ('이슈 제목 47', '이슈 내용 47', 8, 1, 1, NOW(), NULL),
    ('이슈 제목 54', '이슈 내용 54', 7, 2, 1, NOW(), NULL),
    ('이슈 제목 60', '이슈 내용 60', 10, 4, 1, NOW(), NULL),
    ('이슈 제목 69', '이슈 내용 69', 9, 2, 1, NOW(), NULL),
    ('이슈 제목 70', '이슈 내용 70', 8, 2, 1, NOW(), NULL),
    ('이슈 제목 75', '이슈 내용 75', 8, 5, 1, NOW(), NULL),
    ('이슈 제목 84', '이슈 내용 84', 4, 4, 1, NOW(), NULL),
    ('이슈 제목 95', '이슈 내용 95', 9, 1, 1, NOW(), NULL);

-- Issue_Label
INSERT INTO issue_label (issue_id, label_id, last_modified_at)
VALUES
    (14, 1, NOW()),
    (18, 2, NOW()),
    (24, 2, NOW()),
    (46, 4, NOW()),
    (47, 5, NOW()),
    (54, 2, NOW()),
    (60, 3, NOW()),
    (69, 2, NOW()),
    (70, 1, NOW()),
    (75, 3, NOW()),
    (84, 4, NOW()),
    (95, 5, NOW());

-- Issue_Assignee
INSERT INTO issue_assignee (issue_id, assignee_id, last_modified_at)
VALUES
    (14, 7, NOW()),
    (18, 7, NOW()),
    (24, 8, NOW()),
    (46, 8, NOW()),
    (47, 6, NOW()),
    (54, 5, NOW()),
    (60, 7, NOW()),
    (69, 3, NOW()),
    (70, 3, NOW()),
    (75, 1, NOW()),
    (84, 9, NOW()),
    (95, 7, NOW());

-- Comments
INSERT INTO comments (issue_id, content, author_id, last_modified_at, image_url)
VALUES
    (14, '댓글 내용 16', 10, NOW(), NULL),
    (14, '댓글 내용 17', 1, NOW(), NULL),
    (18, '댓글 내용 21', 8, NOW(), NULL),
    (18, '댓글 내용 22', 4, NOW(), NULL),
    (24, '댓글 내용 27', 4, NOW(), NULL),
    (24, '댓글 내용 28', 8, NOW(), NULL),
    (46, '댓글 내용 50', 4, NOW(), NULL),
    (47, '댓글 내용 51', 8, NOW(), NULL),
    (47, '댓글 내용 52', 4, NOW(), NULL),
    (54, '댓글 내용 57', 4, NOW(), NULL),
    (60, '댓글 내용 59', 3, NOW(), NULL),
    (60, '댓글 내용 60', 1, NOW(), NULL),
    (69, '댓글 내용 86', 6, NOW(), NULL),
    (69, '댓글 내용 87', 5, NOW(), NULL),
    (70, '댓글 내용 88', 9, NOW(), NULL),
    (70, '댓글 내용 89', 2, NOW(), NULL),
    (75, '댓글 내용 70', 10, NOW(), NULL),
    (75, '댓글 내용 71', 6, NOW(), NULL),
    (84, '댓글 내용 83', 3, NOW(), NULL),
    (95, '댓글 내용 97', 10, NOW(), NULL);
