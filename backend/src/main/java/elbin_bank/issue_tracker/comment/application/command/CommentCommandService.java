package elbin_bank.issue_tracker.comment.application.command;

import elbin_bank.issue_tracker.comment.application.query.CommentsQueryRepository;
import elbin_bank.issue_tracker.comment.domain.Comment;
import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import elbin_bank.issue_tracker.comment.exception.CommentNotFoundException;
import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentUpdateRequestDto;
import elbin_bank.issue_tracker.common.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;
    private final CommentsQueryRepository commentsQueryRepository;

    @Transactional
    public void createComment(CommentCreateRequestDto requestDto, Long id, Long userId) {
        Comment comment = Comment.of(id, userId, requestDto.content());

        commentCommandRepository.save(comment);
    }

    @Transactional
    public void updateComment(CommentUpdateRequestDto commentUpdateRequestDto, Long id, Long commentId, Long userId) {
        List<CommentProjection> comments = commentsQueryRepository.findByIssueId(id);

        CommentProjection comment = comments.stream()
                .filter(commentProjection -> Objects.equals(commentProjection.id(), commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (comment.userId() != userId) {
            throw new ForbiddenException("You are not authorized to update this comment.");
        }

        commentCommandRepository.updateComment(comment, commentUpdateRequestDto);
    }

}
