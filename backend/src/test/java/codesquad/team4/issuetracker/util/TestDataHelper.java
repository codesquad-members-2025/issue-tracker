package codesquad.team4.issuetracker.util;

import java.time.LocalDate;
import java.util.Map;

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
    public static void insertIssueAllParams(JdbcTemplate jdbcTemplate, Long id, String title, boolean isOpen, Long authorId, String content, String fileUrl, Long milestoneId) {
        jdbcTemplate.update("""
        INSERT INTO issue (issue_id, title, is_open, author_id, content, file_url, milestone_id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """, id, title, isOpen, authorId, content, fileUrl, milestoneId);
    }
    public static void insertMilestone(JdbcTemplate jdbcTemplate, Long id, String name, String description, LocalDate endDate, Boolean isOpen) {
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

    public static void insertIssueLabel(JdbcTemplate jdbcTemplate, Long id, Long issueId, Long labelId) {
        jdbcTemplate.update("""
            INSERT INTO issue_label (issue_label_id, issue_id, label_id)
            VALUES (?, ?, ?)
        """, id, issueId, labelId);
    }

    public static void insertIssueAssignee(JdbcTemplate jdbcTemplate, Long id, Long issueId, Long assigneeId) {
        jdbcTemplate.update("""
            INSERT INTO issue_assignee (issue_assignee_id, issue_id, assignee_id)
            VALUES (?, ?, ?)
        """, id, issueId, assigneeId);
    }
    public static void insertComment(JdbcTemplate jdbcTemplate, Long id, String content, Long authorId, Long issueId, String fileUrl) {
        jdbcTemplate.update("""
        INSERT INTO comment (comment_id, content, author_id, issue_id, file_url)
        VALUES (?, ?, ?, ?, ?)
    """, id, content, authorId, issueId, fileUrl);
    }

    public static void insertSummaryCount(JdbcTemplate jdbcTemplate,
                                          Map<String, Integer> summaryMap) {
        String sql = "UPDATE summary_count SET cnt = ? WHERE type = ?";

        for (Map.Entry<String, Integer> entry : summaryMap.entrySet()) {
            jdbcTemplate.update(sql, entry.getValue(), entry.getKey());
        }
    }
}
