package elbin_bank.issue_tracker.issue.presentation.query;

import elbin_bank.issue_tracker.issue.application.query.IssueQueryService;
import elbin_bank.issue_tracker.issue.application.query.dto.*;
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
    public ResponseEntity<IssueDetailResponseDto> getIssue(@PathVariable("id") long id) {
        return ResponseEntity.ok(issueQueryService.getIssue(id));
    }

    @GetMapping("{id}/labels")
    public ResponseEntity<LabelsResponseDto> getLabelsForIssue(@PathVariable("id") long id) {
        return ResponseEntity.ok(issueQueryService.getLabelsRelatedToIssue(id));
    }

    @GetMapping("{id}/assignees")
    public ResponseEntity<AssigneesResponseDto> getAssigneesForIssue(@PathVariable("id") long id) {
        return ResponseEntity.ok(issueQueryService.getAssigneesRelatedToIssue(id));
    }

    @GetMapping("{id}/milestone")
    public ResponseEntity<MilestoneResponseDto> getMilestoneForIssue(@PathVariable("id") long id) {
        return ResponseEntity.ok(issueQueryService.getMilestoneForIssue(id));
    }

}
