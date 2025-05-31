package elbin_bank.issue_tracker.comment.presentation.command;

import elbin_bank.issue_tracker.comment.application.command.CommentCommandService;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class CommentCommandController {

    private final CommentCommandService commentCommandService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto, @PathVariable Long id) {
        commentCommandService.createComment(commentCreateRequestDto, id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateRequestDto commentUpdateRequestDto, @PathVariable Long id, @PathVariable Long commentId) {
        commentCommandService.updateComment(commentUpdateRequestDto, id, commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
