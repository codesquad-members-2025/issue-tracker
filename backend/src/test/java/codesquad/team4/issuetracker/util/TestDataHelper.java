package codesquad.team4.issuetracker.util;

import java.time.LocalDateTime;
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
    public static void insertIssueAllParams(JdbcTemplate jdbcTemplate, Long id, String title, boolean isOpen, Long authorId, String content, String imageUrl, Long milestoneId) {
        jdbcTemplate.update("""
        INSERT INTO issue (issue_id, title, is_open, author_id, content, image_url, milestone_id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, id, title, isOpen, authorId, content, imageUrl, milestoneId);
    }
    public static void insertMilestone(JdbcTemplate jdbcTemplate, Long id, String name, String description, LocalDateTime endDate, Boolean isOpen) {
        jdbcTemplate.update("""
        INSERT INTO milestone (milestone_id, name, description, end_date, is_open)
        VALUES (?, ?, ?, ?, ?)
    """, id, name, description, endDate, isOpen);
    }

    public static void insertLabel(JdbcTemplate jdbcTemplate, Long id, String title, String color) {
        jdbcTemplate.update("""
        INSERT INTO label (label_id, name, color)
        VALUES (?, ?, ?)
    """, id, title, color);
    }
}
