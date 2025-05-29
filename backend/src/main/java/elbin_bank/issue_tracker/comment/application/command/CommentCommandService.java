package elbin_bank.issue_tracker.comment.application.command;

import elbin_bank.issue_tracker.comment.domain.Comment;
import elbin_bank.issue_tracker.comment.domain.CommentCommandRepository;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentCommandRepository commentCommandRepository;

    public void createComment(CommentCreateRequestDto requestDto, Long id) {
        long mockUserId = 1L; // todo: 로그인 구현 후 변경 예정

        Comment comment = Comment.of(id, mockUserId, requestDto.content());

        commentCommandRepository.save(comment);
    }

}
