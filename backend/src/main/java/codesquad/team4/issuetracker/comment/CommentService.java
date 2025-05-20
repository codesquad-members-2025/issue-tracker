package codesquad.team4.issuetracker.comment;

import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.entity.Comment;
import codesquad.team4.issuetracker.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private static final String CREATE_ISSUE = "댓글이 생성되었습니다.";
    private static final String UPDATE_COMMENT = "댓글이 수정되었습니다.";

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto.CreateCommentDto createComment(CommentRequestDto.CreateCommentDto request, String uploadUrl) {
        Comment comment = Comment.builder()
                .content(request.getContent())
                .imageUrl(uploadUrl)
                .issueId(request.getIssueId())
                .authorId(request.getAuthorId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.CreateCommentDto.builder()
                .id(savedComment.getId())
                .message(CREATE_ISSUE)
                .build();
    }

    @Transactional
    public CommentResponseDto.UpdateCommentDto updateComment(Long commentId, CommentRequestDto.UpdateCommentDto request, String uploadUrl) {
        //댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        comment.updateContent(request.getContent());
        comment.updateImageUrl(uploadUrl);

        commentRepository.save(comment);

        return CommentResponseDto.UpdateCommentDto.builder()
                .id(commentId)
                .message(UPDATE_COMMENT)
                .build();
    }
}
