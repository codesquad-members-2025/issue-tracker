package elbin_bank.issue_tracker.issue.infrastructure;

import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueRepository;
import elbin_bank.issue_tracker.issue.domain.IssueStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcIssueRepository implements IssueRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcIssueRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Issue> findByFilter(Boolean isClosed) {
        IssueQueryBuilder builder = new IssueQueryBuilder()
                .filterClosed(isClosed);
        String sql = builder.buildSql();

        return jdbc.query(sql, builder.getParams(),
                BeanPropertyRowMapper.newInstance(Issue.class));
    }

    @Override
    public long countByStatus(IssueStatus status) {
        String sql = "SELECT COUNT(*) FROM Issue WHERE is_closed = :st";
        Long count = jdbc.queryForObject(sql,
                new MapSqlParameterSource("st", status == IssueStatus.CLOSED),
                Long.class
        );

        if (count == null) {
            return 0;
        }

        return count;
    }

}
