DROP TABLE IF EXISTS `issue_label`;
DROP TABLE IF EXISTS `assignee`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `issue`;
DROP TABLE IF EXISTS `label`;
DROP TABLE IF EXISTS `milestone`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `issue_status_count`;

CREATE TABLE `user`
(
    `id`                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `github_id`         BIGINT UNSIGNED NULL,
    `login`             VARCHAR(255) NULL,
    `password`          VARCHAR(255) NULL,
    `nickname`          VARCHAR(255) NOT NULL,
    `profile_image_url` VARCHAR(1000) NULL,
    `uuid`              VARCHAR(255) NOT NULL,
    `created_at`        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`        DATETIME NULL,
    UNIQUE KEY `user_uuid_unique` (`uuid`)
);

CREATE TABLE `milestone`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `is_closed`     BOOLEAN      NOT NULL,
    `title`         VARCHAR(255) NOT NULL,
    `description`   TEXT NULL,
    `expired_at`    DATE NULL,
    `total_issues`  BIGINT       NOT NULL DEFAULT 0,
    `closed_issues` BIGINT       NOT NULL DEFAULT 0,
    `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`    DATETIME NULL
);

CREATE TABLE `issue`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `author_id`    BIGINT UNSIGNED NOT NULL,
    `milestone_id` BIGINT UNSIGNED NULL,
    `title`        VARCHAR(255) NOT NULL,
    `contents`     TEXT         NULL,
    `is_closed`    BOOLEAN      NOT NULL,
    `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`   DATETIME NULL,
    FOREIGN KEY (`author_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`id`)
);

CREATE TABLE `label`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(255) NOT NULL UNIQUE,
    `description` VARCHAR(255) NULL,
    `color`       VARCHAR(255) NOT NULL,
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME NULL
);

CREATE TABLE `comment`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `issue_id`   BIGINT UNSIGNED NOT NULL,
    `user_id`    BIGINT UNSIGNED NOT NULL,
    `contents`   TEXT     NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL,
    FOREIGN KEY (`issue_id`) REFERENCES `issue` (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `assignee`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`  BIGINT UNSIGNED NOT NULL,
    `issue_id` BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`issue_id`) REFERENCES `issue` (`id`)
);

CREATE TABLE `issue_label`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `issue_id` BIGINT UNSIGNED NOT NULL,
    `label_id` BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (`issue_id`) REFERENCES `issue` (`id`),
    FOREIGN KEY (`label_id`) REFERENCES `label` (`id`)
);
