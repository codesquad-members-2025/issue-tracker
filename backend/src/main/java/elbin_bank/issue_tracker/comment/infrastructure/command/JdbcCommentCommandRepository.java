package elbin_bank.issue_tracker.comment.infrastructure.command;

import elbin_bank.issue_tracker.comment.domain.Comment;
import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCommentCommandRepository implements CommentCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void save(Comment comment) {
        String sql = """
                    INSERT INTO comment
                      (issue_id, user_id, contents)
                    VALUES
                      (:issueId, :userId, :contents)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("issueId", comment.getIssueId())
                .addValue("userId", comment.getUserId())
                .addValue("contents", comment.getContents());

        jdbc.update(sql, params);
    }

}
