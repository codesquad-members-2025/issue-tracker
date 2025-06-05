-- mysql -u root -p --local-infile=1 으로 로그인
-- user.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/user.csv'
INTO TABLE user
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, username, email, image_url, password, created_at, updated_at);

-- milestone.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/milestone.csv'
INTO TABLE milestone
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, deadline, name, description, is_open, created_at, updated_at);

-- label.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/label.csv'
INTO TABLE label
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, name, description, text_color, background_color, created_at, updated_at);

-- issue.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/issue.csv'
INTO TABLE issue
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, title, user_id, milestone_id, is_open, created_at, updated_at);

-- comment.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/comment.csv'
INTO TABLE comment
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, user_id, issue_id, content, created_at, updated_at);

-- issue_label.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/issue_label.csv'
INTO TABLE issue_label
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, issue_id, label_id);

-- issue_assignee.csv
LOAD DATA LOCAL INFILE 'C:/Users/user/Desktop/codesquad_issue_tracker/issue-tracker/dummydata/issue_assignee.csv'
INTO TABLE issue_assignee
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id, issue_id, assignee_id);
