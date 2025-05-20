package CodeSquad.IssueTracker.comment;

import CodeSquad.IssueTracker.comment.dto.CommentRequestDto;
import CodeSquad.IssueTracker.comment.dto.CommentResponseDto;
import CodeSquad.IssueTracker.comment.dto.CommentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/{issueId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public CommentResponseDto writeComment(@RequestBody CommentRequestDto request,Long authorId) {
        return commentService.createComment(request,authorId);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateDto request,Long requesterId) {
        return commentService.update(commentId,request,requesterId);
    }


}
