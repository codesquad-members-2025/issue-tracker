CREATE TABLE milestone (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           deadline DATETIME NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           description TEXT,
                           is_open BOOLEAN NOT NULL
);
CREATE TABLE user (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      image_url VARCHAR(255),
                      password VARCHAR(255) NOT NULL
);
CREATE TABLE issue (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(255) NOT NULL,
                       body TEXT,
                       image_url VARCHAR(255),
                       user_id BIGINT NOT NULL,
                       milestone_id BIGINT,
                       is_open BOOLEAN NOT NULL,
                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES user(id),
                       FOREIGN KEY (milestone_id) REFERENCES milestone(id)
);
CREATE TABLE comment (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         user_id BIGINT NOT NULL,
                         issue_id BIGINT NOT NULL,
                         content TEXT NOT NULL,
                         image_url VARCHAR(255),
                         created_at DATETIME NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES user(id),
                         FOREIGN KEY (issue_id) REFERENCES issue(id)
);
CREATE TABLE label (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       color VARCHAR(50)
);
CREATE TABLE issue_label (
                             issue_id BIGINT NOT NULL,
                             label_id BIGINT NOT NULL,
                             PRIMARY KEY (issue_id, label_id),
                             FOREIGN KEY (issue_id) REFERENCES issue(id),
                             FOREIGN KEY (label_id) REFERENCES label(id)
);
