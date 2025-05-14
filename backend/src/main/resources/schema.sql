DROP TABLE IF EXISTS `issue_label`;
DROP TABLE IF EXISTS `assignee`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `issue`;
DROP TABLE IF EXISTS `label`;
DROP TABLE IF EXISTS `milestone`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `github_id`         BIGINT UNSIGNED NULL,
    `login`             VARCHAR(255) NULL,
    `password`          VARCHAR(255) NULL,
    `nickname`          VARCHAR(255) NOT NULL,
    `profile_image_url` VARCHAR(255) NULL,
    `uuid`              VARCHAR(255) NOT NULL,
    `created_at`        DATETIME     NOT NULL,
    `updated_at`        DATETIME NULL,
    `deleted_at`        DATETIME NULL,
    UNIQUE KEY `user_uuid_unique` (`uuid`)
);

CREATE TABLE `milestone`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `is_closed`   BOOLEAN      NOT NULL,
    `title`       VARCHAR(255) NOT NULL,
    `description` TEXT NULL,
    `expired_at`  DATE NULL,
    `created_at`  DATETIME     NOT NULL,
    `updated_at`  DATETIME NULL,
    `deleted_at`  DATETIME NULL
);

CREATE TABLE `issue`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `author_id`    BIGINT UNSIGNED NOT NULL,
    `milestone_id` BIGINT UNSIGNED NULL,
    `title`        VARCHAR(255) NOT NULL,
    `contents`     TEXT         NOT NULL,
    `is_closed`    BOOLEAN      NOT NULL,
    `file_path`    VARCHAR(255) NULL,
    `created_at`   DATETIME     NOT NULL,
    `updated_at`   DATETIME NULL,
    `deleted_at`   DATETIME NULL,
    FOREIGN KEY (`author_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`milestone_id`) REFERENCES `milestone` (`id`)
);

CREATE TABLE `label`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    `color`       VARCHAR(255) NOT NULL,
    `created_at`  DATETIME     NOT NULL,
    `updated_at`  DATETIME NULL,
    `deleted_at`  DATETIME NULL
);

CREATE TABLE `comment`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `label_id`   BIGINT UNSIGNED NULL,
    `user_id`    BIGINT UNSIGNED NOT NULL,
    `contents`   TEXT     NOT NULL,
    `file_path`  VARCHAR(255) NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NULL,
    `deleted_at` DATETIME NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY (`label_id`) REFERENCES `label` (`id`)
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
