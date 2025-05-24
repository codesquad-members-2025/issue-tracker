package elbin_bank.issue_tracker.issue.presentation;

import elbin_bank.issue_tracker.issue.application.query.IssueQueryService;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueQueryController {

    private final IssueQueryService issueQueryService;

    @GetMapping("")
    public ResponseEntity<IssuesResponseDto> list(@RequestParam(value = "q", required = false) String q) {
        return ResponseEntity.ok(issueQueryService.getFilteredIssues(q));
    }

}
