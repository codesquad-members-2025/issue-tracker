package codesquad.team01.issuetracker.issue.api;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issues")
    public ResponseEntity<ApiResponse<IssueDto.ListResponse>> getIssues(
            @Valid IssueDto.QueryRequest request) {

        log.info("이슈 목록 조회 요청: state={}, writerId={}, milestoneId={}, labelIds={}, assigneeIds={}",
                request.getState(), request.getWriterId(), request.getMilestoneId(),
                request.getLabelIds(), request.getAssigneeIds());

        IssueDto.ListResponse response =
                issueService.findIssues(
                        request.getIssueState(),
                        request.getWriterId(),
                        request.getMilestoneId(),
                        request.getLabelIds(),
                        request.getAssigneeIds()
                );

        log.info("조건에 부합하는 이슈 개수= {}", response.totalCount());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
