package elbin_bank.issue_tracker.milestone.infrastructure.query;

import elbin_bank.issue_tracker.milestone.application.query.repository.MilestoneQueryRepository;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneShortProjection;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMilestoneQueryRepository implements MilestoneQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcMilestoneQueryRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<MilestoneShortProjection> findAllForSelectBox() {
        String sql = """
                SELECT
                  m.id   AS id,
                  m.title AS title
                FROM milestone m
                WHERE m.deleted_at IS NULL
                ORDER BY m.id
                """;

        return jdbc.query(
                sql,
                new MapSqlParameterSource(),
                (rs, rn) -> new MilestoneShortProjection(
                        rs.getLong("id"),
                        rs.getString("title")
                )
        );
    }

    @Override
    public List<MilestoneProjection> findAllDetailed() {
        String sql = """
                SELECT
                  m.id            AS id,
                  m.title         AS title,
                  m.total_issues  AS totalIssueCount,
                  m.closed_issues AS closedIssueCount
                FROM milestone m
                WHERE m.deleted_at IS NULL
                ORDER BY m.id
                """;

        return jdbc.query(
                sql,
                new MapSqlParameterSource(),
                (rs, rn) -> new MilestoneProjection(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("totalIssueCount"),
                        rs.getLong("closedIssueCount")
                )
        );
    }

    @Override
    public MilestoneProjection findByIssueId(long issueId) {
        String sql = """
                SELECT
                  m.id            AS id,
                  m.title         AS title,
                  m.total_issues  AS totalIssueCount,
                  m.closed_issues AS closedIssueCount
                FROM milestone m
                JOIN issue i ON i.milestone_id = m.id
                WHERE i.id = :issueId AND m.deleted_at IS NULL
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("issueId", issueId);

        return jdbc.queryForObject(
                sql,
                params,
                (rs, rn) -> new MilestoneProjection(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("totalIssueCount"),
                        rs.getLong("closedIssueCount")
                )
        );
    }

}
