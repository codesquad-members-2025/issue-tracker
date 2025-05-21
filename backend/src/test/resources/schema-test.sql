-- USER
CREATE TABLE `user` (
                        user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        nickname VARCHAR(255) NOT NULL UNIQUE,
                        profile_image VARCHAR(255),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- MILESTONE
CREATE TABLE milestone (
                           milestone_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(50) NOT NULL UNIQUE,
                           description VARCHAR(50),
                           end_date DATE,
                           is_open BOOLEAN,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- LABEL
CREATE TABLE label (
                       label_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(20) NOT NULL UNIQUE,
                       description VARCHAR(50),
                       color VARCHAR(20),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ISSUE
CREATE TABLE issue (
                       issue_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(255) NOT NULL,
                       content VARCHAR(1000),
                       file_url VARCHAR(255),
                       author_id BIGINT NOT NULL,
                       milestone_id BIGINT,
                       is_open BOOLEAN DEFAULT true,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       FOREIGN KEY (author_id) REFERENCES `user`(user_id),
                       FOREIGN KEY (milestone_id) REFERENCES milestone(milestone_id)
);

-- COMMENT
CREATE TABLE comment (
                         comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         content VARCHAR(1000) NOT NULL,
                         author_id BIGINT NOT NULL,
                         issue_id BIGINT NOT NULL,
                         file_url VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES `user`(user_id),
                         CONSTRAINT fk_comment_issue FOREIGN KEY (issue_id) REFERENCES issue(issue_id)
);

-- ISSUE_LABEL (다대다 관계)
CREATE TABLE issue_label (
                             issue_label_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             issue_id BIGINT NOT NULL,
                             label_id BIGINT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT fk_issue_label_issue FOREIGN KEY (issue_id) REFERENCES issue(issue_id),
                             CONSTRAINT fk_issue_label_label FOREIGN KEY (label_id) REFERENCES label(label_id)
);

-- ISSUE_ASSIGNEE (다대다 관계)
CREATE TABLE issue_assignee (
                                issue_assignee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                issue_id BIGINT NOT NULL,
                                assignee_id BIGINT NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_issue_assignee_issue FOREIGN KEY (issue_id) REFERENCES issue(issue_id),
                                CONSTRAINT fk_issue_assignee_user FOREIGN KEY (assignee_id) REFERENCES `user`(user_id)
);
