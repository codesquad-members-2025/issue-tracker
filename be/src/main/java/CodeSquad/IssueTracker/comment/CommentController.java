package CodeSquad.IssueTracker.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{issueId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public Comment writeComment(@PathVariable Long issueId, @RequestBody Comment comment) {
        return commentService.save(comment);
    }
}
