    package codesquad.team4.issuetracker.issue;

    import static codesquad.team4.issuetracker.util.IssueFilteringParser.camelToSnake;

    import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
    import codesquad.team4.issuetracker.issue.dto.IssueRequestDto.IssueFilterParamDto;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;
    import java.util.stream.Collectors;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
    import org.springframework.stereotype.Repository;

    @Repository
    @Slf4j
    @RequiredArgsConstructor
    public class IssueDao {

        private final JdbcTemplate jdbcTemplate;
        private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        private record SqlWithParams(String sql, Map<String, Object> params) {}
        public List<Map<String, Object>> findIssuesByOpenStatus(IssueRequestDto.IssueFilterParamDto dto, int page, int size){

            SqlWithParams sqlWithParams = buildPagedQuery(dto, page, size);
            log.info(sqlWithParams.sql());
            return namedParameterJdbcTemplate.queryForList(sqlWithParams.sql, sqlWithParams.params());
        }

        private SqlWithParams buildPagedQuery(IssueFilterParamDto dto, int page, int size) {

            StringBuilder sql = new StringBuilder("WITH paged_issues AS (\n");
            Map<String, Object> params = new HashMap<>();

            sql.append(buildIssueIdFilterSubquery(dto, params));

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

            return new SqlWithParams(sql.toString(), params);
        }

        private String buildIssueIdFilterSubquery(IssueRequestDto.IssueFilterParamDto dto, Map<String, Object> params) {
            StringBuilder sql = new StringBuilder("SELECT issue_id FROM issue WHERE is_open = :isOpen\n");
            params.put("isOpen", dto.getStatus().getState());

            // 작성자 필터
            addfilteringConditionDynamic(dto, params, sql);

            return sql.toString();
        }

        public Integer countIssuesByOpenStatus(boolean isOpen) {
            String sql = "SELECT COUNT(*) FROM issue WHERE is_open = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, isOpen);
        }
        public Map<String, Integer> countFilteredIssues(IssueFilterParamDto dto) {
            StringBuilder sql = new StringBuilder("WITH filtered_issues AS (\n");
            Map<String, Object> params = new HashMap<>();

            sql.append("SELECT is_open FROM issue WHERE 1=1\n");

            // 동적 필터링
            addfilteringConditionDynamic(dto, params, sql);

            sql.append(")\n");
            sql.append("SELECT is_open, COUNT(*) as count FROM filtered_issues GROUP BY is_open");

            List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql.toString(), params);

            return getCountMap(result);
        }

        private void addfilteringConditionDynamic(IssueFilterParamDto dto, Map<String, Object> params, StringBuilder sql) {
            // 작성자 필터
            addAuthorCondition(dto.getAuthorId(), params, sql);
            // 담당자 필터
            addAssigneeCondition(dto.getAssigneeId(), params, sql);
            // 댓글 작성자 필터
            addCommentAuthorCondition(dto.getCommentAuthorId(), params, sql);
            // 마일스톤 필터
            addMilestoneCondition(dto.getMilestoneId(), params, sql);
            // 라벨 필터
            addLabelConditions(dto.getLabelIds(), params, sql);
        }

        private void addLabelConditions(Set<Long> labelIds, Map<String, Object> params, StringBuilder sql) {
            // 라벨 필터링 - IN + GROUP BY + HAVING
            if (labelIds != null && !labelIds.isEmpty()) {
                sql.append(" AND issue_id IN (\n");
                sql.append("SELECT il.issue_id \nFROM issue_label il \nWHERE il.label_id IN (");

                List<Long> labelList = new ArrayList<>(labelIds);
                List<String> labelParamNames = new ArrayList<>();
                for (int i = 0; i < labelIds.size(); i++) {
                    String paramName = "label" + i;
                    labelParamNames.add(":" + paramName);
                    params.put(paramName, labelList.get(i));
                }

                sql.append(String.join(", ", labelParamNames));
                sql.append(") \nGROUP BY il.issue_id HAVING COUNT(DISTINCT il.label_id) = :labelCount)\n");
                params.put("labelCount", labelIds.size());
            }
        }

        private void addCommentAuthorCondition(Long commentAuthorId, Map<String, Object> params, StringBuilder sql) {
            addExistsCondition("comment", "issue_id", "author_id", commentAuthorId, "commentAuthorId", params, sql);
        }

        private void addAssigneeCondition(Long assigneeId, Map<String, Object> params, StringBuilder sql) {
            addExistsCondition("issue_assignee", "issue_id", "assignee_id", assigneeId, "assigneeId", params, sql);
        }

        private void addExistsCondition(String subTable, String joinColumn, String filterColumn, Long filterValue,
                                        String paramName, Map<String, Object> params, StringBuilder sql) {
            if (filterValue != null) {
                sql.append(" AND EXISTS (\n")
                    .append("     SELECT 1 FROM ").append(subTable).append(" sub\n")
                    .append("     WHERE sub.").append(joinColumn).append(" = issue.issue_id\n")
                    .append("       AND sub.").append(filterColumn).append(" = :").append(paramName).append("\n")
                    .append(")\n");
                params.put(paramName, filterValue);
            }
        }

        private void addMilestoneCondition(Long milestoneId, Map<String, Object> params, StringBuilder sql) {
            addWhereEqualClause(milestoneId, params, "milestoneId", sql);
        }

        private void addAuthorCondition(Long authorId, Map<String, Object> params, StringBuilder sql) {
            addWhereEqualClause(authorId, params, "authorId", sql);
        }

        private void addWhereEqualClause(Long id, Map<String, Object> params, String paramName, StringBuilder sql) {
            if (id != null) {
                sql.append(" AND ")
                    .append(camelToSnake(paramName))
                    .append(" = :")
                    .append(paramName)
                    .append("\n");
                params.put(paramName, id);
            }
        }

        private Map<String, Integer> getCountMap(List<Map<String, Object>> result) {
            int open = 0, close = 0;
            for (Map<String, Object> row : result) {
                Boolean isOpen = (Boolean) row.get("is_open");
                int count = ((Number) row.get("count")).intValue();
                if (Boolean.TRUE.equals(isOpen)) {
                    open = count;
                } else {
                    close = count;
                }
            }

            Map<String, Integer> counts = new HashMap<>();
            counts.put("open", open);
            counts.put("close", close);
            return counts;
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
