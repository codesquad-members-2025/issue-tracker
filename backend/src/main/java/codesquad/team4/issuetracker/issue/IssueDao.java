    package codesquad.team4.issuetracker.issue;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    import lombok.RequiredArgsConstructor;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
    import org.springframework.stereotype.Repository;

    @Repository
    @RequiredArgsConstructor
    public class IssueDao {

        private final JdbcTemplate jdbcTemplate;
        private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        public List<Map<String, Object>> findIssuesByOpenStatus(boolean isOpen, int page, int size){
            int offset = Math.max(0, (page - 1) * size);

            String sql = """
                SELECT i.issue_id AS issue_id,
                       i.title,
                       u.user_id AS author_id,
                       u.nickname AS author_nickname,
                       u.profile_image AS author_profile,
                       m.milestone_id AS milestone_id,
                       m.name AS milestone_title,
                       l.label_id AS label_id,
                       l.name AS label_name,
                       l.color AS label_color,
                       a.user_id AS assignee_id,
                       a.nickname AS assignee_nickname,
                       a.profile_image AS assignee_profile
                FROM issue i
                LEFT JOIN `user` u ON i.author_id = u.user_id
                LEFT JOIN milestone m ON i.milestone_id = m.milestone_id
                JOIN issue_label il ON i.issue_id = il.issue_id
                LEFT JOIN label l ON il.label_id = l.label_id
                JOIN issue_assignee ia ON i.issue_id = ia.issue_id
                LEFT JOIN `user` a ON ia.assignee_id = a.user_id
                WHERE i.is_open = ?
                LIMIT ? OFFSET ?
            """;

            return jdbcTemplate.queryForList(sql, isOpen, size, offset);
        }

        public int countIssuesByOpenStatus(boolean isOpen) {
            return jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM issue WHERE is_open = ?",
                    Integer.class,
                    isOpen
            );
        }

        public List<Long> findExistingIssueIds(List<Long> issueIds) {
            String placeholders = issueIds.stream().map(id -> "?").collect(Collectors.joining(", "));
            String sql = "SELECT issue_id FROM issue WHERE issue_id IN (" + placeholders + ")";
            return jdbcTemplate.query(sql,
                    (rs, rowNum) -> rs.getLong("issue_id"),
                    issueIds.toArray());
        }

        public int updateIssueStatusByIds(boolean isOpen, List<Long> issueIds) {
            String placeholders = issueIds.stream().map(id -> "?").collect(Collectors.joining(", "));
            String updateSql = "UPDATE issue SET is_open = ? WHERE issue_id IN (" + placeholders + ")";

            List<Object> params = new ArrayList<>();
            params.add(isOpen);
            params.addAll(issueIds);

            return jdbcTemplate.update(updateSql, params.toArray());
        }

        public List<Map<String, Object>> findIssueDetailById(Long issueId) {
            String sql = """
                SELECT
                    i.content AS issue_content,
                    i.image_url AS issue_image_url,
                    c.comment_id AS comment_id,
                    c.content AS comment_content,
                    c.image_url AS comment_image_url,
                    c.created_at AS comment_created_at,
                    u.user_id AS author_id,
                    u.nickname AS author_nickname,
                    u.profile_image AS author_profile
                FROM issue i
                JOIN comment c ON i.issue_id = c.issue_id
                JOIN user u ON u.user_id = c.author_id
                where i.issue_id = ?
            """;

            return jdbcTemplate.queryForList(sql, issueId);
        }
    }
