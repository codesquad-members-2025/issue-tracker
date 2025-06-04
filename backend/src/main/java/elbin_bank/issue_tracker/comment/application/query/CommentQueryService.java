package elbin_bank.issue_tracker.comment.application.query;

import elbin_bank.issue_tracker.comment.application.query.dto.CommentDto;
import elbin_bank.issue_tracker.comment.application.query.dto.CommentsResponseDto;
import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;
import elbin_bank.issue_tracker.user.application.query.repository.UserQueryRepository;
import elbin_bank.issue_tracker.user.infrastructure.query.projection.UserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentQueryService {

    private final CommentsQueryRepository commentsQueryRepository;
    private final UserQueryRepository userQueryRepository;

    @Transactional(readOnly = true)
    public CommentsResponseDto findByIssueId(Long issueId) {
        List<CommentProjection> comments = commentsQueryRepository.findByIssueId(issueId);

        List<Long> userIds = comments.stream().map(CommentProjection::userId).toList();

        Map<Long, UserProjection> commentUsers = userQueryRepository.findUsersByIds(userIds);

        return new CommentsResponseDto(comments.stream()
                .map(comment -> new CommentDto(
                        comment.id(),
                        commentUsers.get(comment.userId()),
                        comment.content(),
                        comment.createdAt(),
                        comment.updatedAt()
                ))
                .toList());
    }

}
