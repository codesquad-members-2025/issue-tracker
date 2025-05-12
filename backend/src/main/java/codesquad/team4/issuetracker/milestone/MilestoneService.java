package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final JdbcTemplate jdbcTemplate;

    public MilestoneCountDto getMilestoneCount() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM milestone", Integer.class);

        return new MilestoneCountDto(count);
    }
}
