package codesquad.team01.issuetracker.issue.api;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.issue.dto.IssueListResponse;
import codesquad.team01.issuetracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issues")
    public ResponseEntity<ApiResponse<IssueListResponse>> getIssues(
            @RequestParam(defaultValue = "open") String state,
            @RequestParam(required = false) Long writer,
            @RequestParam(required = false) Long milestone,
            @RequestParam(required = false) String labels,
            @RequestParam(required = false) String assignees) {

        IssueListResponse response = issueService.findIssues(state, writer, milestone, labels, assignees);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
