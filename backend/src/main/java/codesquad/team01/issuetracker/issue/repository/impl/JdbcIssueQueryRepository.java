package codesquad.team01.issuetracker.issue.repository.impl;

import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcIssueQueryRepository implements IssueQueryRepository {

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
            f.url as writer_profile_image_url,
            m.id as milestone_id,
            m.title as milestone_title
        FROM issue i
        JOIN users u ON i.writer_id = u.id
        LEFT JOIN file f ON u.profile_image_id = f.id
        LEFT JOIN milestone m ON i.milestone_id = m.id
        WHERE 1=1
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

    @Override
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

        // labelIds - AND
        if (labelIds != null && !labelIds.isEmpty()) {
            for (int i = 0; i < labelIds.size(); i++) {
                sql.append("""
                 AND EXISTS (
                    SELECT 1 FROM issue_label il2 
                    WHERE il2.issue_id = i.id 
                    AND il2.label_id = :labelId""").append(i).append("""
                )
                """);
                params.addValue("labelId" + i, labelIds.get(i));
            }
        }

        // assigneeIds - AND
        if (assigneeIds != null && !assigneeIds.isEmpty()) {
            for (int i = 0; i < assigneeIds.size(); i++) {
                sql.append("""
                 AND EXISTS (
                    SELECT 1 FROM issue_assignee ia2 
                    WHERE ia2.issue_id = i.id 
                    AND ia2.user_id = :assigneeId""").append(i).append("""
                )
                """);
                params.addValue("assigneeId" + i, assigneeIds.get(i));
            }
        }

        // 정렬: 생성일자 내림차순 (최신순)
        sql.append(" ORDER BY i.created_at DESC, i.id DESC");

        return jdbcTemplate.query(sql.toString(), params, issueRowMapper);
    }
}
