

-- 2. 기존 테이블 삭제 (의존 순서 고려)
DROP TABLE IF EXISTS issue_label;
DROP TABLE IF EXISTS issue_assignee;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS issues;
DROP TABLE IF EXISTS labels;
DROP TABLE IF EXISTS milestones;
DROP TABLE IF EXISTS users;



-- 4. 테이블 생성

-- users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       login_id VARCHAR(255),
                       password VARCHAR(255),
                       email_id VARCHAR(255),
                       nick_name VARCHAR(255)
);

-- milestones
CREATE TABLE milestones (
                            milestone_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100),
                            description VARCHAR(255),
                            end_date TIMESTAMP,
                            is_open BOOLEAN,
                            created_at TIMESTAMP,
                            updated_at TIMESTAMP
);

-- labels
CREATE TABLE labels (
                        label_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50),
                        description VARCHAR(255),
                        color VARCHAR(20),
                        created_at TIMESTAMP
);

-- issues
CREATE TABLE issues (
                        issue_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255),
                        content TEXT,
                        image_url VARCHAR(255),
                        author_id BIGINT,
                        milestone_id BIGINT,
                        is_open BOOLEAN,
                        comment_id BIGINT,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        FOREIGN KEY (author_id) REFERENCES users(id),
                        FOREIGN KEY (milestone_id) REFERENCES milestones(milestone_id)
);

-- comments
CREATE TABLE comments (
                          comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          content TEXT,
                          image_url VARCHAR(255),
                          author_id BIGINT,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP,
                          FOREIGN KEY (author_id) REFERENCES users(id)
);

-- issue_assignee
CREATE TABLE issue_assignee (
                                issue_assignee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                issue_id BIGINT,
                                assignee_id BIGINT,
                                created_at TIMESTAMP,
                                updated_at TIMESTAMP,
                                FOREIGN KEY (issue_id) REFERENCES issues(issue_id),
                                FOREIGN KEY (assignee_id) REFERENCES users(id)
);

-- issue_label
CREATE TABLE issue_label (
                             issue_label_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             issue_id BIGINT,
                             label_id BIGINT,
                             created_at TIMESTAMP,
                             updated_at TIMESTAMP,
                             FOREIGN KEY (issue_id) REFERENCES issues(issue_id),
                             FOREIGN KEY (label_id) REFERENCES labels(label_id)
);

ALTER TABLE issues
    DROP COLUMN comment_id;

ALTER TABLE comments
    ADD COLUMN issue_id BIGINT;

ALTER TABLE comments
    ADD CONSTRAINT fk_comments_issue
        FOREIGN KEY (issue_id) REFERENCES issues(issue_id)
            ON DELETE CASCADE;
