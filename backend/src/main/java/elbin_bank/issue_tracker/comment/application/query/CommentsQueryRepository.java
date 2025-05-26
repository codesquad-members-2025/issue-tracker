package elbin_bank.issue_tracker.comment.application.query;

import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;

import java.util.List;

public interface CommentsQueryRepository {

    List<CommentProjection> findByIssueId(Long issueId);

}
