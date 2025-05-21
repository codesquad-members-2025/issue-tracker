package elbin_bank.issue_tracker.comment.application.query;

import elbin_bank.issue_tracker.comment.application.query.dto.CommentsSummaryDto;
import elbin_bank.issue_tracker.comment.application.query.dto.CommentsResponseDto;
import elbin_bank.issue_tracker.comment.application.query.dto.UserDto;
import elbin_bank.issue_tracker.comment.domain.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class GetCommentsListService {

    private final CommentsQueryRepository commentsQueryRepository;

    public GetCommentsListService(CommentsQueryRepository commentsQueryRepository) {
        this.commentsQueryRepository = commentsQueryRepository;
    }

    @Transactional(readOnly = true)
    public CommentsResponseDto findByIssueId(Long issueId) {
        List<Comment> comments = commentsQueryRepository.findByIssueId(issueId);

        List<Long> userIds = comments.stream().map(Comment::getUserId).toList();

        Map<Long, UserDto> commentUsers = commentsQueryRepository.findUsersByIds(userIds);

        return new CommentsResponseDto(comments.stream()
                .map(comment -> new CommentsSummaryDto(
                        comment.getId(),
                        commentUsers.get(comment.getUserId()),
                        comment.getContents(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ))
                .toList());
    }

}
