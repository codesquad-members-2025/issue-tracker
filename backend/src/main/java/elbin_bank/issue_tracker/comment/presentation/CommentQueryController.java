package elbin_bank.issue_tracker.comment.presentation;

import elbin_bank.issue_tracker.comment.application.query.CommentQueryService;
import elbin_bank.issue_tracker.comment.application.query.dto.CommentsResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/issues")
public class CommentQueryController {

    private final CommentQueryService commentQueryService;

    public CommentQueryController(CommentQueryService commentQueryService) {
        this.commentQueryService = commentQueryService;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsResponseDto> getComments(@PathVariable long id) {
        try{
            return ResponseEntity.ok(commentQueryService.findByIssueId(id));
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

}
