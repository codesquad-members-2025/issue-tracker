package elbin_bank.issue_tracker.comment.application.command;

import elbin_bank.issue_tracker.comment.application.query.CommentsQueryRepository;
import elbin_bank.issue_tracker.comment.domain.Comment;
import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import elbin_bank.issue_tracker.comment.exception.CommentNotFoundException;
import elbin_bank.issue_tracker.comment.infrastructure.query.projection.CommentProjection;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentUpdateRequestDto;
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
    public void createComment(CommentCreateRequestDto requestDto, Long id) {
        long mockUserId = 1L; // todo: 로그인 구현 후 변경 예정

        Comment comment = Comment.of(id, mockUserId, requestDto.content());

        commentCommandRepository.save(comment);
    }

    @Transactional
    public void updateComment(CommentUpdateRequestDto commentUpdateRequestDto, Long id, Long commentId) {
        List<CommentProjection> comments = commentsQueryRepository.findByIssueId(id);

        CommentProjection comment = comments.stream()
                .filter(commentProjection -> Objects.equals(commentProjection.id(), commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        commentCommandRepository.updateComment(comment, commentUpdateRequestDto);
    }

}
