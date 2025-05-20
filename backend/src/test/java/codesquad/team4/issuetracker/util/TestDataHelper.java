package codesquad.team4.issuetracker.util;

import org.springframework.jdbc.core.JdbcTemplate;

public class TestDataHelper {

    public static void insertUser(JdbcTemplate jdbcTemplate, Long id, String nickname) {
        jdbcTemplate.update("""
            INSERT INTO `user` (user_id, email, nickname)
            VALUES (?, ?, ?)
        """, id, "user" + id + "@test.com", nickname);
    }

    public static void insertIssue(JdbcTemplate jdbcTemplate, Long id, String title, boolean isOpen, Long authorId) {
        jdbcTemplate.update("""
            INSERT INTO issue (issue_id, title, is_open, author_id)
            VALUES (?, ?, ?, ?)
        """, id, title, isOpen, authorId);
    }
}