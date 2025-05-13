package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final JdbcTemplate jdbcTemplate;

    public MilestoneFilterDto getFilterMilestones() {
        String sql = "SELECT milestone_id, name FROM milestone";

        List<MilestoneFilterDto.MilestoneInfo> milestones = jdbcTemplate.query(sql, (rs, rowNum) ->
                MilestoneFilterDto.MilestoneInfo.builder()
                        .id(rs.getLong("milestone_id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return MilestoneFilterDto.builder()
                .milestones(milestones)
                .count(milestones.size())
                .build();
    }
}
