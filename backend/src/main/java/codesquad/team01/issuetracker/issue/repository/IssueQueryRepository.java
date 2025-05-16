package codesquad.team01.issuetracker.issue.repository;

import codesquad.team01.issuetracker.issue.dto.IssueJoinRow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IssueQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String BASE_QUERY = """
        SELECT 
            i.id as issue_id,
            i.title as issue_title,
            i.is_open as issue_is_open,
            i.created_at as issue_created_at,
            i.updated_at as issue_updated_at,
            u.id as writer_id,
            u.username as writer_username,
            u.profile_image_url as writer_profile_image_url,
            m.id as milestone_id,
            m.title as milestone_title,
            a.id as assignee_id,
            a.profile_image_url as assignee_profile_image_url,
            l.id as label_id,
            l.name as label_name,
            l.color as label_color,
            l.text_color as label_text_color
        FROM Issue i
        JOIN Users u ON i.writer_id = u.id
        LEFT JOIN Milestone m ON i.milestone_id = m.id
        LEFT JOIN IssueAssignee ia ON i.id = ia.issue_id
        LEFT JOIN Users a ON ia.user_id = a.id
        LEFT JOIN IssueLabel il ON i.id = il.issue_id
        LEFT JOIN Label l ON il.label_id = l.id
        WHERE 1=1
        """;

    private final RowMapper<IssueJoinRow> issueJoinRowMapper = new RowMapper<IssueJoinRow>() {
        @Override
        public IssueJoinRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new IssueJoinRow(
                    rs.getLong("issue_id"),
                    rs.getString("issue_title"),
                    rs.getBoolean("issue_is_open"),
                    rs.getTimestamp("issue_created_at").toLocalDateTime(),
                    rs.getTimestamp("issue_updated_at").toLocalDateTime(),
                    rs.getLong("writer_id"),
                    rs.getString("writer_username"),
                    rs.getString("writer_profile_image_url"),
                    rs.getObject("milestone_id", Long.class),
                    rs.getString("milestone_title"),
                    rs.getObject("assignee_id", Long.class),
                    rs.getString("assignee_profile_image_url"),
                    rs.getObject("label_id", Long.class),
                    rs.getString("label_name"),
                    rs.getString("label_color"),
                    rs.getString("label_text_color")
            );
        }
    };

    public List<IssueJoinRow> findIssuesWithFilters(
            String state, Long writer, Long milestone,
            List<Long> labelIds, List<Long> assigneeIds) {

        StringBuilder sql = new StringBuilder(BASE_QUERY);
        MapSqlParameterSource params = new MapSqlParameterSource();

        // state - 기본값: open
        sql.append(" AND i.is_open = :isOpen");
        params.addValue("isOpen", "open".equals(state));

        // writer
        if (writer != null) {
            sql.append(" AND i.writer_id = :writer");
            params.addValue("writer", writer);
        }

        // milestone
        if (milestone != null) {
            sql.append(" AND i.milestone_id = :milestone");
            params.addValue("milestone", milestone);
        }

        // labels - OR
        if (labelIds != null && !labelIds.isEmpty()) {
            sql.append("""
                 AND EXISTS (
                    SELECT 1 FROM IssueLabel il2 
                    WHERE il2.issue_id = i.id 
                    AND il2.label_id IN (:labelIds)
                )
                """);
            params.addValue("labelIds", labelIds);
        }

        // assignees - OR
        if (assigneeIds != null && !assigneeIds.isEmpty()) {
            sql.append("""
                 AND EXISTS (
                    SELECT 1 FROM IssueAssignee ia2 
                    WHERE ia2.issue_id = i.id 
                    AND ia2.user_id IN (:assigneeIds)
                )
                """);
            params.addValue("assigneeIds", assigneeIds);
        }

        // 정렬: 생성일자 내림차순 (최신순)
        sql.append(" ORDER BY i.created_at DESC, i.id DESC");

        return jdbcTemplate.query(sql.toString(), params, issueJoinRowMapper);
    }
}