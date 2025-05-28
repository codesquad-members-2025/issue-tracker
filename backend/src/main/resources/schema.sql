DROP TABLE IF EXISTS issue_assignee;
DROP TABLE IF EXISTS issue_label;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS label;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS user;

CREATE TABLE milestone
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    deadline    DATE,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    is_open     BOOLEAN      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL
);
CREATE TABLE user
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(255) NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    image_url  VARCHAR(255),
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL
);
CREATE TABLE issue
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    user_id      BIGINT       NOT NULL,
    milestone_id BIGINT,
    is_open      BOOLEAN      NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (milestone_id) REFERENCES milestone (id) ON DELETE SET NULL
);
CREATE TABLE comment
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT,
    issue_id   BIGINT    NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE SET NULL,
    FOREIGN KEY (issue_id) REFERENCES issue (id) ON DELETE CASCADE
);
CREATE TABLE label
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(255) NOT NULL UNIQUE,
    description      TEXT,
    text_color       VARCHAR(50)  NOT NULL,
    background_color VARCHAR(50)  NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    updated_at       TIMESTAMP    NOT NULL
);
CREATE TABLE issue_label
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    issue_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,
    UNIQUE (issue_id, label_id),
    FOREIGN KEY (issue_id) REFERENCES issue (id) ON DELETE CASCADE,
    FOREIGN KEY (label_id) REFERENCES label (id) ON DELETE CASCADE
);
CREATE TABLE issue_assignee
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    issue_id    BIGINT NOT NULL,
    assignee_id BIGINT NOT NULL,
    UNIQUE (issue_id, assignee_id),
    FOREIGN KEY (issue_id) REFERENCES issue (id) ON DELETE CASCADE,
    FOREIGN KEY (assignee_id) REFERENCES user (id) ON DELETE CASCADE
);
