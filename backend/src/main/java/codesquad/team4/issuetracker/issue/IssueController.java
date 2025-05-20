package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.exception.FileUploadException;
import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.exception.IssueNotFoundException;
import codesquad.team4.issuetracker.exception.IssueStatusUpdateException;
import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.CreateIssueDto;
import codesquad.team4.issuetracker.response.ApiResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<IssueResponseDto.CreateIssueDto> createIssue(
            @RequestPart("issue") @Valid IssueRequestDto.CreateIssueDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        String uploadUrl;

        try {
            uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY).orElse(EMPTY);
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().body(
                    IssueResponseDto.CreateIssueDto.builder()
                    .message(ExceptionMessage.FILE_UPLOAD_FAILED)
                    .build());
        }

        IssueResponseDto.CreateIssueDto result = issueService.createIssue(request, uploadUrl);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/status")
    public ResponseEntity<IssueResponseDto.BulkUpdateIssueStatusDto> updateIssueStatus(
            @RequestBody IssueRequestDto.BulkUpdateIssueStatusDto requestDto) {
        try {
            IssueResponseDto.BulkUpdateIssueStatusDto result = issueService.bulkUpdateIssueStatus(requestDto);
            return ResponseEntity.ok(result);
        } catch (IssueStatusUpdateException e) {
            return ResponseEntity.badRequest().body(
                    IssueResponseDto.BulkUpdateIssueStatusDto.builder()
                            .message(ExceptionMessage.UPDATE_ISSUE_FAILED)
                            .build()
            );

        }
    }

    @GetMapping("/{issue-id}")
    public ResponseEntity<ApiResponse<IssueResponseDto.searchIssueDetailDto>> searchIssueDetail(@PathVariable("issue-id") Long issueId){
        IssueResponseDto.searchIssueDetailDto result = issueService.getIssueDetailById(issueId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PatchMapping(value = "/{issue-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<IssueResponseDto.CreateIssueDto>> updateIssue(
            @PathVariable("issue-id") Long issueId,
            @RequestPart("issue") @Valid IssueRequestDto.IssueUpdateDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY).orElse(EMPTY);
        IssueResponseDto.CreateIssueDto result = issueService.updateIssue(issueId, request, uploadUrl);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
