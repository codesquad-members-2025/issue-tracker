package elbin_bank.issue_tracker.user.infrastructure.query;

import elbin_bank.issue_tracker.user.infrastructure.query.projection.UserProjection;
import elbin_bank.issue_tracker.user.application.query.repository.UserQueryRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserQueryRepository implements UserQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcUserQueryRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Map<Long, String> findNickNamesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }

        String sql = "SELECT id, nickname FROM user WHERE id IN (:ids) AND deleted_at IS NULL";
        Map<String, Object> params = Map.of("ids", ids);

        return jdbc.query(sql, params, rs -> {
            Map<Long, String> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getLong("id"), rs.getString("nickname"));
            }
            return result;
        });
    }

    @Override
    public Map<Long, UserProjection> findUsersByIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Map.of();
        }

        String sql = """
                SELECT id, nickname, profile_image_url
                FROM user
                WHERE id IN (:Ids)
                AND deleted_at IS NULL
                """;

        var params = new MapSqlParameterSource("Ids", userIds);

        return jdbc.query(sql, params, rs -> {
            Map<Long, UserProjection> result = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String nickname = rs.getString("nickname");
                String profileImageUrl = rs.getString("profile_image_url");

                UserProjection dto = new UserProjection(id, nickname, profileImageUrl);
                result.put(id, dto);
            }
            return result;
        });
    }

    @Override
    public List<UserProjection> findAll() {
        String sql = """
                SELECT
                  u.id,
                  u.nickname,
                  u.profile_image_url
                FROM `user` u
                WHERE u.deleted_at IS NULL
                """;

        return jdbc.query(sql, new MapSqlParameterSource(), (rs, rowNum) ->
                new UserProjection(
                        rs.getLong("id"),
                        rs.getString("nickname"),
                        rs.getString("profile_image_url")
                ));
    }

}
