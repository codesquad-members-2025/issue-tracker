package elbin_bank.issue_tracker.comment.presentation.query;

import elbin_bank.issue_tracker.comment.application.query.CommentQueryService;
import elbin_bank.issue_tracker.comment.application.query.dto.CommentsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsResponseDto> getComments(@PathVariable long id) {
        return ResponseEntity.ok(commentQueryService.findByIssueId(id));
    }

}
