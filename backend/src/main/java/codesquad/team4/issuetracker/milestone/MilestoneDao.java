package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import java.util.List;
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
}
