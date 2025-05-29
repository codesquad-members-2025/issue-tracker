package elbin_bank.issue_tracker.comment.presentation.command;

import elbin_bank.issue_tracker.comment.application.command.CommentCommandService;
import elbin_bank.issue_tracker.comment.presentation.command.dto.request.CommentCreateRequestDto;
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

}
