package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MilestoneDao {

    private final JdbcTemplate jdbcTemplate;

    public List<MilestoneResponseDto.MilestoneInfo> findMilestoneForFiltering() {
        String sql = "SELECT milestone_id, name FROM milestone";

       return jdbcTemplate.query(sql, (rs, rowNum) ->
                MilestoneResponseDto.MilestoneInfo.builder()
                        .id(rs.getLong("milestone_id"))
                        .title(rs.getString("name"))
                        .build()
        );
    }

    public Integer countMilestonesByOpenStatus(boolean isOpen) {
        return jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM milestone WHERE is_open = ?",
            Integer.class,
            isOpen
        );
    }

    public List<Map<String, Object>> findMilestonesByOpenStatus(boolean isOpen) {
        StringBuilder sql = new StringBuilder("""
            SELECT m.milestone_id AS id,
            m.name,
            m.description,
            m.end_date,
            COUNT(CASE WHEN i.is_open = true THEN 1 END) AS open_issue_count,
            COUNT(CASE WHEN i.is_open = false THEN 1 END) AS closed_issue_count
            FROM milestone m
            LEFT JOIN issue i ON m.milestone_id = i.milestone_id
            WHERE m.is_open = ?
            GROUP BY m.milestone_id
            ORDER BY m.created_at DESC
            """);
        return jdbcTemplate.queryForList(sql.toString(), isOpen);
    }
}
