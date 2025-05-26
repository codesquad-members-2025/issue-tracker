package elbin_bank.issue_tracker.comment.infrastructure.query;

import elbin_bank.issue_tracker.comment.application.query.CommentsQueryRepository;
import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcCommentsQueryRepository implements CommentsQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<CommentProjection> findByIssueId(Long issueId) {
        String sql = "SELECT id, user_id, contents, created_at, updated_at FROM comment WHERE issue_id = :issueId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", issueId);

        RowMapper<CommentProjection> rowMapper = (rs, rowNum) -> new CommentProjection(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("contents"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );

        return jdbc.query(sql, params, rowMapper);
    }

}
