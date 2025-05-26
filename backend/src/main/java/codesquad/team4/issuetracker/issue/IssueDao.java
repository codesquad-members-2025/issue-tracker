    package codesquad.team4.issuetracker.issue;

    import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;

    import java.util.*;
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

        public List<Map<String, Object>> findIssuesByOpenStatus(IssueRequestDto.IssueFilterParamDto dto, int page, int size){

            StringBuilder sql = new StringBuilder("""
        WITH paged_issues AS (
            SELECT issue_id
            FROM issue
            WHERE is_open = :isOpen
    """);

            Map<String, Object> params = new HashMap<>();
            params.put("isOpen", dto.getStatus().getState()); // dto.getIsOpen()도 가능

            if (dto.getAuthorId() != null) {
                sql.append(" AND author_id = :authorId");
                params.put("authorId", dto.getAuthorId());
            }

            if (dto.getAssigneeId() != null) {
                sql.append("""
            AND EXISTS (
                SELECT 1 FROM issue_assignee ia
                WHERE ia.issue_id = issue.issue_id
                AND ia.assignee_id = :assigneeId
            )
        """);
                params.put("assigneeId", dto.getAssigneeId());
            }

            if (dto.getCommentAuthorId() != null) {
                sql.append("""
            AND EXISTS (
                SELECT 1 FROM comment c
                WHERE c.issue_id = issue.issue_id
                AND c.author_id = :commentAuthorId
            )
        """);
                params.put("commentAuthorId", dto.getCommentAuthorId());
            }

            if (dto.getMilestoneId() != null) {
                sql.append(" AND milestone_id = :milestoneId");
                params.put("milestoneId", dto.getMilestoneId());
            }

            // 라벨 필터링 - IN + GROUP BY + HAVING
            if (dto.getLabelId() != null && !dto.getLabelId().isEmpty()) {
                sql.append(" AND issue_id IN (");
                sql.append("SELECT il.issue_id FROM issue_label il WHERE il.label_id IN (");

                List<String> labelParamNames = new ArrayList<>();
                for (int i = 0; i < dto.getLabelId().size(); i++) {
                    String paramName = "label" + i;
                    labelParamNames.add(":" + paramName);
                    params.put(paramName, dto.getLabelId().get(i));
                }

                sql.append(String.join(", ", labelParamNames));
                sql.append(") GROUP BY il.issue_id HAVING COUNT(DISTINCT il.label_id) = :labelCount)");
                params.put("labelCount", dto.getLabelId().size());
            }

            sql.append("""
        ORDER BY created_at DESC
        LIMIT :size OFFSET :offset
        )
        SELECT
            i.issue_id AS issue_id,
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
        JOIN paged_issues p ON i.issue_id = p.issue_id
        LEFT JOIN `user` u ON i.author_id = u.user_id
        LEFT JOIN milestone m ON i.milestone_id = m.milestone_id
        LEFT JOIN issue_label il ON i.issue_id = il.issue_id
        LEFT JOIN label l ON il.label_id = l.label_id
        LEFT JOIN issue_assignee ia ON i.issue_id = ia.issue_id
        LEFT JOIN `user` a ON ia.assignee_id = a.user_id
        ORDER BY i.created_at DESC
    """);

            params.put("size", size);
            params.put("offset", page * size);

            return namedParameterJdbcTemplate.queryForList(sql.toString(), params);
        }

        public Integer countIssuesByOpenStatus(boolean isOpen) {
            String sql = "SELECT COUNT(*) FROM issue WHERE is_open = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, isOpen);
        }

        public Set<Long> findExistingIssueIds(List<Long> issueIds) {
            String placeholders = issueIds.stream().map(id -> "?").collect(Collectors.joining(", "));
            String sql = "SELECT issue_id FROM issue WHERE issue_id IN (" + placeholders + ")";
            List<Long> results = jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getLong("issue_id"),
                issueIds.toArray());
            return new HashSet<>(results);
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
                    i.file_url AS issue_file_url,
                    c.comment_id AS comment_id,
                    c.content AS comment_content,
                    c.file_url AS comment_file_url,
                    c.created_at AS comment_created_at,
                    u.user_id AS author_id,
                    u.nickname AS author_nickname,
                    u.profile_image AS author_profile
                FROM issue i
                LEFT JOIN comment c ON i.issue_id = c.issue_id
                LEFT JOIN `user` u ON u.user_id = c.author_id
                where i.issue_id = ?
                ORDER BY c.created_at DESC
            """;

            return jdbcTemplate.queryForList(sql, issueId);
        }
    }
