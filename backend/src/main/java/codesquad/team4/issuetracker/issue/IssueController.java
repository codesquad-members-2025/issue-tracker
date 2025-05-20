package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.exception.FileUploadException;
import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.exception.IssueStatusUpdateException;
import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.ApiMessageDto;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {
    private static final String EMPTY = "";
    private static final String ISSUE_DIRECTORY = "issues/";

    private final IssueService issueService;
    private final S3FileService s3FileService;

    @GetMapping("")
    public ResponseEntity<IssueResponseDto.IssueListDto> showIssueList(@RequestParam(name = "is_open") boolean isOpen, Pageable pageable) {

        IssueResponseDto.IssueListDto issues = issueService.getIssues(isOpen, pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(issues);
    }

    @GetMapping("/count")
    public ResponseEntity<IssueCountDto> showIssueCount() {
        IssueCountDto result = issueService.getIssueCounts();
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiMessageDto> createIssue(
            @RequestPart("issue") @Valid IssueRequestDto.CreateIssueDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY).orElse(EMPTY);

        ApiMessageDto result = issueService.createIssue(request, uploadUrl);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/status")
    public ResponseEntity<IssueResponseDto.BulkUpdateIssueStatusDto> updateIssueStatus(
            @RequestBody IssueRequestDto.BulkUpdateIssueStatusDto requestDto) {

        IssueResponseDto.BulkUpdateIssueStatusDto result = issueService.bulkUpdateIssueStatus(requestDto);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{issue-id}")
    public ResponseEntity<ApiResponse<IssueResponseDto.searchIssueDetailDto>> searchIssueDetail(@PathVariable("issue-id") Long issueId){
        IssueResponseDto.searchIssueDetailDto result = issueService.getIssueDetailById(issueId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PatchMapping(value = "/{issue-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ApiMessageDto>> updateIssue(
            @PathVariable("issue-id") Long issueId,
            @RequestPart("issue") @Valid IssueRequestDto.IssueUpdateDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY).orElse(EMPTY);
        ApiMessageDto result = issueService.updateIssue(issueId, request, uploadUrl);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PutMapping("/{issueId}/labels")
    public ResponseEntity<ApiResponse<IssueResponseDto.ApiMessageDto>> updateIssueLabels(@PathVariable Long issueId, @RequestBody IssueRequestDto.IssueLabelsUpdateDto request) {

        ApiMessageDto result = issueService.updateLabels(issueId, request.getLabels());

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PutMapping("/{issueId}/assignees")
    public ResponseEntity<ApiResponse<IssueResponseDto.ApiMessageDto>> updateIssueAssignees(@PathVariable Long issueId, @RequestBody IssueRequestDto.IssueAssigneeUpdateDto request) {

        ApiMessageDto result = issueService.updateAssignees(issueId, request.getAssignees());

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
