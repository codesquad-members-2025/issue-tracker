package elbin_bank.issue_tracker.issue.presentation;

import elbin_bank.issue_tracker.issue.application.query.IssueQueryService;
import elbin_bank.issue_tracker.issue.application.query.dto.IssueDetailResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueQueryController {

    private final IssueQueryService issueQueryService;

    @GetMapping("")
    public ResponseEntity<IssuesResponseDto> list(@RequestParam(value = "q", required = false) String dsl) {
        return ResponseEntity.ok(issueQueryService.getFilteredIssues(dsl));
    }

    @GetMapping("{id}")
    public ResponseEntity<IssueDetailResponseDto> getIssue(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(issueQueryService.getIssue(id));
    }

}
