-- User 테이블 (작성자, 담당자, 프로필 이미지 url)
CREATE TABLE IF NOT EXISTS `Users` (
                                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       `username` VARCHAR(100) NOT NULL UNIQUE,
    `profile_image_url` VARCHAR(500)
    );

-- Milestone
CREATE TABLE IF NOT EXISTS `Milestone` (
                                           `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           `title` VARCHAR(500) NOT NULL
    );

-- Label
CREATE TABLE IF NOT EXISTS `Label` (
                                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       `name` VARCHAR(100) NOT NULL UNIQUE,
    `color` VARCHAR(7) NOT NULL DEFAULT '#0075ca',
    `text_color` VARCHAR(10) NOT NULL DEFAULT 'white'
    );

-- Issue
CREATE TABLE IF NOT EXISTS `Issue` (
                                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       `title` VARCHAR(500) NOT NULL,
    `is_open` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `writer_id` BIGINT NOT NULL,
    `milestone_id` BIGINT,
    FOREIGN KEY (`writer_id`) REFERENCES `Users`(`id`),
    FOREIGN KEY (`milestone_id`) REFERENCES `Milestone`(`id`)
    );

-- IssueAssignee
CREATE TABLE IF NOT EXISTS `IssueAssignee` (
                                               `issue_id` BIGINT NOT NULL,
                                               `user_id` BIGINT NOT NULL,
                                               PRIMARY KEY (`issue_id`, `user_id`),
    FOREIGN KEY (`issue_id`) REFERENCES `Issue`(`id`),
    FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`)
    );

-- IssueLabel 테이블
CREATE TABLE IF NOT EXISTS `IssueLabel` (
                                            `issue_id` BIGINT NOT NULL,
                                            `label_id` BIGINT NOT NULL,
                                            PRIMARY KEY (`issue_id`, `label_id`),
    FOREIGN KEY (`issue_id`) REFERENCES `Issue`(`id`),
    FOREIGN KEY (`label_id`) REFERENCES `Label`(`id`)
    );