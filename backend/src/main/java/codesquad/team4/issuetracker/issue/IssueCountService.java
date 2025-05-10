package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueCountService {

    private final JdbcTemplate jdbcTemplate;

    public IssueCountDto getIssueCounts() {
        //// 쿼리 결과가 null일 경우 NPE 방지를 위해 int 대신 Integer 사용 -> queryForObject(...)가 null반환 할 수 있음
        Integer openCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM issue WHERE is_open = true", Integer.class
        );
        Integer closedCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM issue WHERE is_open = false", Integer.class
        );

        return IssueCountDto.builder()
                .openCount(openCount)
                .closedCount(closedCount)
                .build();
    }
}
