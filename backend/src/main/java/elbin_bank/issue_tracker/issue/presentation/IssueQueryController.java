package elbin_bank.issue_tracker.issue.presentation;

import elbin_bank.issue_tracker.issue.application.query.GetFilteredIssueListService;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueQueryController {
    private final GetFilteredIssueListService getFilteredIssueListService;

    public IssueQueryController(GetFilteredIssueListService getFilteredIssueListService) {
        this.getFilteredIssueListService = getFilteredIssueListService;
    }

    @GetMapping("")
    public ResponseEntity<IssuesResponseDto> list(@RequestParam(value = "q", required = false) String q) {
        try {
            return ResponseEntity.ok(getFilteredIssueListService.find(q));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
