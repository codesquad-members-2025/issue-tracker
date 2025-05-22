package elbin_bank.issue_tracker.user.infrastructure;

import elbin_bank.issue_tracker.user.domain.UserRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Map<Long, String> findNickNamesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }

        String sql = "SELECT id, nickname FROM user WHERE id IN (:ids)";
        Map<String, Object> params = Map.of("ids", ids);

        return jdbc.query(sql, params, rs -> {
            Map<Long, String> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getLong("id"), rs.getString("nickname"));
            }
            return result;
        });
    }

}
