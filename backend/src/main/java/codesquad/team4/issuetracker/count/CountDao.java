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

    public MilestoneCountDto getMilestoneCount() {
        var sql = """
            SELECT milestone_open_count, milestone_closed_count
              FROM summary_count
             WHERE id = 1
        """;
        return jdbcTemplate.queryForObject(sql, (rs, rn) ->
            MilestoneCountDto.builder()
                .openCount  (rs.getInt("milestone_open_count"))
                .closedCount(rs.getInt("milestone_closed_count"))
                .build()
        );
    }

    public void incrementIssueOpen() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_open_count = issue_open_count + 1 WHERE id = 1"
        );
    }
    public void decrementIssueOpen() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_open_count = issue_open_count - 1 WHERE id = 1"
        );
    }
    public void incrementIssueClosed() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_closed_count = issue_closed_count + 1 WHERE id = 1"
        );
    }
    public void decrementIssueClosed() {
        jdbcTemplate.update(
            "UPDATE summary_count SET issue_closed_count = issue_closed_count - 1 WHERE id = 1"
        );
    }
    public void incrementMilestoneOpen() {
        jdbcTemplate.update(
            "UPDATE summary_count SET milestone_open_count = milestone_open_count + 1 WHERE id = 1"
        );
    }
    public void decrementMilestoneOpen() {
        jdbcTemplate.update("UPDATE summary_count SET milestone_open_count = milestone_open_count - 1 WHERE id = 1"
        );
    }
    public void incrementMilestoneClosed() {
        jdbcTemplate.update("UPDATE summary_count SET milestone_closed_count = milestone_closed_count + 1 WHERE id = 1"
        );
    }
    public void decrementMilestoneClosed() {
        jdbcTemplate.update("UPDATE summary_count SET milestone_closed_count = milestone_closed_count - 1 WHERE id = 1"
        );
    }
}
