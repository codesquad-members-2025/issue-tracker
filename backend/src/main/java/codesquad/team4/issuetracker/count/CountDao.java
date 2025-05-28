package codesquad.team4.issuetracker.count;

import codesquad.team4.issuetracker.count.dto.IssueCountDto;
import codesquad.team4.issuetracker.count.dto.MilestoneCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CountDao {
    private final JdbcTemplate jdbcTemplate;

    public static final String ISSUE_OPEN        = "issue_open";
    public static final String ISSUE_CLOSED      = "issue_closed";
    public static final String MILESTONE_OPEN    = "milestone_open";
    public static final String MILESTONE_CLOSED  = "milestone_closed";

    public int getCount(String type) {
        return jdbcTemplate.queryForObject(
                "SELECT count FROM summary_count WHERE type = ?",
                Integer.class, type
        );
    }

    public void adjustCount(String type, int delta) {
        jdbcTemplate.update(
                "UPDATE summary_count SET count = count + ? WHERE type = ?",
                delta, type
        );
    }

    public void increment(String type) {
        adjustCount(type, +1);
    }

    public void decrement(String type) {
        adjustCount(type, -1);
    }
}
