    package codesquad.team4.issuetracker.issue;

    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;
    import lombok.RequiredArgsConstructor;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Repository;

    @Repository
    @RequiredArgsConstructor
    public class IssueDao {

        private final JdbcTemplate jdbcTemplate;

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
                LEFT JOIN user u ON i.author_id = u.user_id
                LEFT JOIN milestone m ON i.milestone_id = m.milestone_id
                JOIN issue_label il ON i.issue_id = il.issue_id
                LEFT JOIN label l ON il.label_id = l.label_id
                JOIN issue_assignee ia ON i.issue_id = ia.issue_id
                LEFT JOIN user a ON ia.assignee_id = a.user_id
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

        public Integer countExistingIssuesByIds(List<Long> issueIds, String placeholders) {
            // 요청받은 id 개수랑 DB에서 가져온 ID 개수 비교
            String countSql = "SELECT COUNT(*) FROM issue WHERE issue_id IN (" + placeholders + ")";

            return jdbcTemplate.queryForObject(countSql, Integer.class, issueIds.toArray());
        }

        public int updateIssueStatus(String placeholders, List<Object> params) {
            String updateSql = "UPDATE issue SET is_open = ? WHERE issue_id IN (" + placeholders + ")";

            return jdbcTemplate.update(updateSql, params.toArray());
        }
    }
