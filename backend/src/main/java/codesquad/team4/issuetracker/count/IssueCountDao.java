package codesquad.team4.issuetracker.count;

import codesquad.team4.issuetracker.count.dto.IssueCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IssueCountDao {

    private final JdbcTemplate jdbcTemplate;

    public IssueCountDto getIssueCounts() {
        var sql = """
            SELECT issue_open_count, issue_closed_count
              FROM summary_count
             WHERE id = 1
        """;
        return jdbcTemplate.queryForObject(sql, (rs, rn) ->
            IssueCountDto.builder()
                .openCount  (rs.getInt("issue_open_count"))
                .closedCount(rs.getInt("issue_closed_count"))
                .build()
        );
    }

    public void incrementOpenCount() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_open_count = issue_open_count + 1 WHERE id = 1"
        );
    }
    public void decrementOpenCount() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_open_count = issue_open_count - 1 WHERE id = 1"
        );
    }
    public void incrementClosedCount() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_closed_count = issue_closed_count + 1 WHERE id = 1"
        );
    }
    public void decrementClosedCount() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_closed_count = issue_closed_count - 1 WHERE id = 1"
        );
    }
}
