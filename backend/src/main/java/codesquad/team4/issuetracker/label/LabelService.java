package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final JdbcTemplate jdbcTemplate;
    public LabelCountDto getLabelCount() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM label", Integer.class);

        return new LabelCountDto(count);
    }
}
