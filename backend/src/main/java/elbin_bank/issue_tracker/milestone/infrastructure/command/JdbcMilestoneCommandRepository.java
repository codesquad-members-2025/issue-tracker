package elbin_bank.issue_tracker.milestone.infrastructure.command;

import elbin_bank.issue_tracker.milestone.domain.Milestone;
import elbin_bank.issue_tracker.milestone.domain.MilestoneCommandRepository;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneUpdateProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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
                .addValue("isClosed", false)
                .addValue("title", milestone.getTitle())
                .addValue("description", milestone.getDescription())
                .addValue("expiredAt", milestone.getExpiredAt());

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


}
