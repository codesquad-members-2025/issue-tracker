package codesquad.team4.issuetracker.comment;

import codesquad.team4.issuetracker.comment.dto.CommentRequestDto;
import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private static final String CREATE_ISSUE = "댓글이 생성되었습니다.";

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
}
