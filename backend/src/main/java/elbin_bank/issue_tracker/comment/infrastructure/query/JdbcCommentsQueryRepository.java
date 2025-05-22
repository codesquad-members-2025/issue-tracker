package elbin_bank.issue_tracker.comment.infrastructure.query;

import elbin_bank.issue_tracker.comment.application.query.dto.UserDto;
import elbin_bank.issue_tracker.comment.domain.Comment;
import elbin_bank.issue_tracker.comment.application.query.CommentsQueryRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCommentsQueryRepository implements CommentsQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcCommentsQueryRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Comment> findByIssueId(Long issueId) {

        String sql = "SELECT id, issue_id, user_id, contents, file_path, created_at, updated_at FROM comment WHERE issue_id = :issueId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", issueId);

        return jdbc.query(sql, params, BeanPropertyRowMapper.newInstance(Comment.class));
    }

    @Override
    public Map<Long, UserDto> findUsersByIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Map.of();
        }

        String sql = """
                SELECT id, nickname, profile_image_url
                FROM user
                WHERE id IN (:Ids)
                """;

        var params = new MapSqlParameterSource("Ids", userIds);

        return jdbc.query(sql, params, rs -> {
            Map<Long, UserDto> result = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String nickname = rs.getString("nickname");
                String profileImageUrl = rs.getString("profile_image_url");

                UserDto dto = new UserDto(id, nickname, profileImageUrl);
                result.put(id, dto);
            }
            return result;
        });
    }

}
