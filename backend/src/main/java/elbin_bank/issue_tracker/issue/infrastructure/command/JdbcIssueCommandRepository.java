package elbin_bank.issue_tracker.issue.infrastructure.command;

import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcIssueCommandRepository implements IssueCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Optional<Issue> findById(long id) {
        String sql = """
                SELECT id,
                       author_id,
                       title,
                       contents, 
                       milestone_id,
                       is_closed
                FROM issue
                WHERE id = :id
                """;

        var params = new MapSqlParameterSource().addValue("id", id);

        return Optional.ofNullable(jdbc.queryForObject(sql, params, (rs, rowNum) -> new Issue(
                rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getObject("milestone_id", Long.class),
                rs.getBoolean("is_closed")
        )));
    }

    @Override
    public Issue save(Issue issue) {
        if (issue.getId() == null) {
            return insert(issue);
        } else {
            update(issue);
            return issue;
        }
    }

    private Issue insert(Issue issue) {
        String sql = """
                    INSERT INTO issue
                      (author_id, title, contents, milestone_id, is_closed)
                    VALUES
                      (:authorId, :title, :contents, :milestoneId, :isClosed)
                """;

        var params = new MapSqlParameterSource()
                .addValue("authorId", issue.getAuthorId())
                .addValue("title", issue.getTitle())
                .addValue("contents", issue.getContents())
                .addValue("milestoneId", issue.getMilestoneId())
                .addValue("isClosed", issue.isClosed());

        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(sql, params, kh, new String[]{"id"});
        long newId = kh.getKey().longValue();

        return new Issue(
                newId,
                issue.getAuthorId(),
                issue.getTitle(),
                issue.getContents(),
                issue.getMilestoneId(),
                issue.isClosed()
        );
    }

    private void update(Issue issue) {
        String sql = """
                    UPDATE issue
                       SET author_id    = :authorId,
                           title        = :title,
                           contents     = :contents,
                           milestone_id = :milestoneId,
                           is_closed    = :isClosed
                     WHERE id           = :id
                """;

        var params = new MapSqlParameterSource()
                .addValue("authorId", issue.getAuthorId())
                .addValue("title", issue.getTitle())
                .addValue("contents", issue.getContents())
                .addValue("milestoneId", issue.getMilestoneId())
                .addValue("isClosed", issue.isClosed())
                .addValue("id", issue.getId());

        jdbc.update(sql, params);
    }

    @Override
    public void updateState(long issueId, boolean isClosed) {
        String sql = """
                UPDATE issue
                SET is_closed = :isClosed
                 WHERE id = :id
                """;
        var p = new MapSqlParameterSource()
                .addValue("isClosed", isClosed)
                .addValue("id", issueId);
        jdbc.update(sql, p);
    }

}
