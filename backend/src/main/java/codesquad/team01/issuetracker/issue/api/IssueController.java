package codesquad.team01.issuetracker.issue.api;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.issue.dto.IssueListResponse;
import codesquad.team01.issuetracker.issue.dto.IssueQueryRequest;
import codesquad.team01.issuetracker.issue.service.IssueService;
import jakarta.validation.Valid;
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
            @Valid IssueQueryRequest request
            ) {

        IssueListResponse response =
                issueService.findIssues(request.state(),
                        request.writer(),
                        request.milestone(),
                        request.labels(),
                        request.assignees());

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
