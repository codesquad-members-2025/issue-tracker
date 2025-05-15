package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MilestoneDao {

    private final JdbcTemplate jdbcTemplate;

    public List<MilestoneDto.MilestoneInfo> findMilestoneForFiltering() {
        String sql = "SELECT milestone_id, name FROM milestone";

       return jdbcTemplate.query(sql, (rs, rowNum) ->
                MilestoneDto.MilestoneInfo.builder()
                        .id(rs.getLong("milestone_id"))
                        .name(rs.getString("name"))
                        .build()
        );
    }
}
