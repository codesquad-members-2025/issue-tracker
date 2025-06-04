package elbin_bank.issue_tracker.milestone.infrastructure.command;

import elbin_bank.issue_tracker.milestone.domain.Milestone;
import elbin_bank.issue_tracker.milestone.domain.MilestoneCommandRepository;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneUpdateProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcMilestoneCommandRepository implements MilestoneCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void save(Milestone milestone) {
        String sql = """
                    INSERT INTO milestone
                      (is_closed, title, description, expired_at)
                    VALUES
                      (:isClosed, :title, :description, :expiredAt)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("isClosed", milestone.isClosed())
                .addValue("title", milestone.getTitle())
                .addValue("description", milestone.getDescription())
                .addValue("expiredAt", milestone.getExpiredAt());

        jdbc.update(sql, params);
    }

    @Override
    public void updateState(long id, boolean isClosed) {
        String sql = """
                    UPDATE milestone
                       SET is_closed = :isClosed
                    WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("isClosed", isClosed)
                .addValue("id", id);

        jdbc.update(sql, params);
    }

    @Override
    public void update(MilestoneUpdateProjection milestone, String title, String description, LocalDate expiredAt) {
        String sql = """
                    UPDATE milestone
                       SET title = :title, description = :description, expired_at = :expiredAt, updated_at = NOW()
                    WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("description", description)
                .addValue("expiredAt", expiredAt)
                .addValue("id", milestone.id());

        jdbc.update(sql, params);
    }

    @Override
    public void deleteById(Long id) {
        String sql = """
                UPDATE milestone
                SET deleted_at = CURRENT_TIMESTAMP
                WHERE id = :id
                """;

        var params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update(sql, params);
    }

    @Override
    public void adjustTotalIssues(long milestoneId, long delta) {
        String sql = """
                    UPDATE milestone
                       SET total_issues = total_issues + :delta
                     WHERE id = :id
                """;
        var params = new MapSqlParameterSource()
                .addValue("delta", delta)
                .addValue("id", milestoneId);
        jdbc.update(sql, params);
    }

    @Override
    public void adjustClosedIssues(long milestoneId, long delta) {
        String sql = """
                    UPDATE milestone
                       SET closed_issues = closed_issues + :delta
                     WHERE id = :id
                """;

        var params = new MapSqlParameterSource()
                .addValue("delta", delta)
                .addValue("id", milestoneId);
        jdbc.update(sql, params);
    }

    @Override
    public Optional<Milestone> findById(Long id) {
        String sql = """
                SELECT id, is_closed, title, description, expired_at, total_issues, closed_issues
                FROM milestone
                WHERE id = :id AND deleted_at IS NULL
                """;

        var params = new MapSqlParameterSource().addValue("id", id);
        return Optional.ofNullable(jdbc.queryForObject(sql, params, (rs, rowNum) ->
            new Milestone(
                rs.getLong("id"),
                rs.getBoolean("is_closed"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getObject("expired_at", LocalDate.class)
            )
        ));
    }


}
