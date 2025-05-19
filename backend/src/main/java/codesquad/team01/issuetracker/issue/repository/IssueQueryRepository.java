package codesquad.team01.issuetracker.issue.repository;

import codesquad.team01.issuetracker.issue.dto.IssueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class IssueQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String BASE_ISSUE_QUERY = """
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
            m.title as milestone_title
        FROM issue i
        JOIN users u ON i.writer_id = u.id
        LEFT JOIN milestoneId m ON i.milestone_id = m.id
        WHERE 1=1
        """;

    // 담당자, 레이블 - 필터 OR vs AND 고민
    private static final String ASSIGNEE_QUERY = """
        SELECT 
            ia.issue_id,
            u.id as assignee_id,
            u.profile_image_url as assignee_profile_image_url
        FROM issue_assignee ia
        JOIN users u ON ia.user_id = u.id
        WHERE ia.issue_id IN (:issueIds)
        """;

    private static final String LABEL_QUERY = """
        SELECT 
            il.issue_id,
            l.id as label_id,
            l.name as label_name,
            l.color as label_color,
            l.text_color as label_text_color
        FROM issue_label il
        JOIN label l ON il.label_id = l.id
        WHERE il.issue_id IN (:issueIds)
        """;

    private final RowMapper<IssueDto.BaseRow> issueRowMapper = (rs, rowNum) -> IssueDto.BaseRow.builder()
            .issueId(rs.getLong("issue_id"))
            .issueTitle(rs.getString("issue_title"))
            .issueOpen(rs.getBoolean("issue_is_open"))
            .issueCreatedAt(rs.getTimestamp("issue_created_at").toLocalDateTime())
            .issueUpdatedAt(rs.getTimestamp("issue_updated_at").toLocalDateTime())
            .writerId(rs.getLong("writer_id"))
            .writerUsername(rs.getString("writer_username"))
            .writerProfileImageUrl(rs.getString("writer_profile_image_url"))
            .milestoneId(rs.getObject("milestone_id", Long.class))
            .milestoneTitle(rs.getString("milestone_title"))
            .build();

    private final RowMapper<IssueDto.AssigneeRow> assigneeRowMapper = (rs, rowNum) -> IssueDto.AssigneeRow.builder()
            .issueId(rs.getLong("issue_id"))
            .assigneeId(rs.getLong("assignee_id"))
            .assigneeProfileImageUrl(rs.getString("assignee_profile_image_url"))
            .build();

    private final RowMapper<IssueDto.LabelRow> labelRowMapper = (rs, rowNum) -> IssueDto.LabelRow.builder()
            .issueId(rs.getLong("issue_id"))
            .labelId(rs.getLong("label_id"))
            .labelName(rs.getString("label_name"))
            .labelColor(rs.getString("label_color"))
            .labelTextColor(rs.getString("label_text_color"))
            .build();


    public List<IssueDto.BaseRow> findIssuesWithFilters(
            String state, Long writerId, Long milestoneId,
            List<Long> labelIds, List<Long> assigneeIds) {

        StringBuilder sql = new StringBuilder(BASE_ISSUE_QUERY);
        MapSqlParameterSource params = new MapSqlParameterSource();

        // state - 기본값: open
        sql.append(" AND i.is_open = :isOpen");
        params.addValue("isOpen", "open".equals(state));

        // writerId
        if (writerId != null) {
            sql.append(" AND i.writer_id = :writerId");
            params.addValue("writerId", writerId);
        }

        // milestoneId
        if (milestoneId != null) {
            sql.append(" AND i.milestone_id = :milestoneId");
            params.addValue("milestoneId", milestoneId);
        }

        // labelIds - OR
        if (labelIds != null && !labelIds.isEmpty()) {
            sql.append("""
                 AND EXISTS (
                    SELECT 1 FROM issue_label il2 
                    WHERE il2.issue_id = i.id 
                    AND il2.label_id IN (:labelIds)
                )
                """);
            params.addValue("labelIds", labelIds);
        }

        // assigneeIds - OR
        if (assigneeIds != null && !assigneeIds.isEmpty()) {
            sql.append("""
                 AND EXISTS (
                    SELECT 1 FROM issue_assignee ia2 
                    WHERE ia2.issue_id = i.id 
                    AND ia2.user_id IN (:assigneeIds)
                )
                """);
            params.addValue("assigneeIds", assigneeIds);
        }

        // 정렬: 생성일자 내림차순 (최신순)
        sql.append(" ORDER BY i.created_at DESC, i.id DESC");

        return jdbcTemplate.query(sql.toString(), params, issueRowMapper);
    }

    public List<IssueDto.AssigneeRow> findAssigneesByIssueIds(List<Long> issueIds) {
        if (issueIds.isEmpty()) {
            return List.of();
        }
        MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);
        return jdbcTemplate.query(ASSIGNEE_QUERY, params, assigneeRowMapper);
    }

    public List<IssueDto.LabelRow> findLabelsByIssueIds(List<Long> issueIds) {
        if (issueIds.isEmpty()) {
            return List.of();
        }
        MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);
        return jdbcTemplate.query(LABEL_QUERY, params, labelRowMapper);
    }
}
