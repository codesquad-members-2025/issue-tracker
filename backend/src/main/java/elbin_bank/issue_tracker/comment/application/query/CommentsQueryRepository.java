package elbin_bank.issue_tracker.comment.application.query;

import elbin_bank.issue_tracker.comment.application.query.dto.UserDto;
import elbin_bank.issue_tracker.comment.domain.Comment;

import java.util.List;
import java.util.Map;

public interface CommentsQueryRepository {

    List<Comment> findByIssueId(Long issueId);

    Map<Long, UserDto> findUsersByIds(List<Long> ids);







}
