package elbin_bank.issue_tracker.comment.infrastructure.command;

import elbin_bank.issue_tracker.comment.domain.Comment;
import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentUpdateRequestDto;
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

    @Override
    public void updateComment(CommentProjection comment, CommentUpdateRequestDto commentUpdateRequestDto) {
        String sql = """
                    UPDATE comment
                       SET contents = :contents,
                           updated_at = NOW()
                     WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("contents", commentUpdateRequestDto.content())
                .addValue("id", comment.id());

        jdbc.update(sql, params);
    }

}
