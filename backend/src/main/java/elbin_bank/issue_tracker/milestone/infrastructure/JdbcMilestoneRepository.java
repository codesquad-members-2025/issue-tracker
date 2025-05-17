package elbin_bank.issue_tracker.milestone.infrastructure;

import elbin_bank.issue_tracker.milestone.domain.MilestoneRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMilestoneRepository implements MilestoneRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcMilestoneRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Map<Long,String> findTitlesByIds(List<Long> milestoneIds) {
        if(milestoneIds == null || milestoneIds.isEmpty()) {
            return Map.of();
        }

        String sql = """
        SELECT id, title
        FROM milestone
        WHERE id IN (:ids)
        """;

        var params = new MapSqlParameterSource("ids", milestoneIds);

        return jdbc.query(sql, params, rs -> {
            Map<Long, String> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getLong("id"), rs.getString("title"));
            }
            return result;
        });

    }

}

