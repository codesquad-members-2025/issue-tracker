package elbin_bank.issue_tracker.comment.domain;

import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentUpdateRequestDto;

public interface CommentCommandRepository {

    void save(Comment comment);

    void updateComment(CommentProjection comment, CommentUpdateRequestDto commentUpdateRequestDto);

}
