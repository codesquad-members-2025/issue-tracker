package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.aws.S3FileService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public ApiResponse<IssueResponseDto.IssueListDto> showIssueList(@RequestParam(name = "is_open") boolean isOpen, Pageable pageable) {

        IssueResponseDto.IssueListDto issues = issueService.getIssues(isOpen, pageable.getPageNumber(), pageable.getPageSize());

        return ApiResponse.success(issues);
    }

    @GetMapping("/count")
    public ApiResponse<IssueCountDto> showIssueCount() {
        IssueCountDto result = issueService.getIssueCounts();
        return ApiResponse.success(result);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<IssueResponseDto.ApiMessageDto> createIssue(
            @RequestPart("issue") @Valid IssueRequestDto.CreateIssueDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY);

        ApiMessageDto result = issueService.createIssue(request, uploadUrl);
        return ApiResponse.success(result);
    }

    @PatchMapping("/status")
    public ApiResponse<IssueResponseDto.BulkUpdateIssueStatusDto> updateIssueStatus(
            @RequestBody IssueRequestDto.BulkUpdateIssueStatusDto requestDto) {

        IssueResponseDto.BulkUpdateIssueStatusDto result = issueService.bulkUpdateIssueStatus(requestDto);

        return ApiResponse.success(result);
    }

    @GetMapping("/{issue-id}")
    public ApiResponse<IssueResponseDto.searchIssueDetailDto> searchIssueDetail(@PathVariable("issue-id") Long issueId){
        IssueResponseDto.searchIssueDetailDto result = issueService.getIssueDetailById(issueId);

        return ApiResponse.success(result);
    }

    @PatchMapping(value = "/{issue-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ApiMessageDto> updateIssue(
            @PathVariable("issue-id") Long issueId,
            @RequestPart("issue") @Valid IssueRequestDto.IssueUpdateDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY);
        ApiMessageDto result = issueService.updateIssue(issueId, request, uploadUrl);

        return ApiResponse.success(result);
    }

    @PutMapping("/{issueId}/labels")
    public ApiResponse<IssueResponseDto.ApiMessageDto> updateIssueLabels(@PathVariable Long issueId, @RequestBody IssueRequestDto.IssueLabelsUpdateDto request) {

        ApiMessageDto result = issueService.updateLabels(issueId, request.getLabels());

        return ApiResponse.success(result);
    }

    @PutMapping("/{issueId}/assignees")
    public ApiResponse<IssueResponseDto.ApiMessageDto> updateIssueAssignees(@PathVariable Long issueId, @RequestBody IssueRequestDto.IssueAssigneeUpdateDto request) {

        ApiMessageDto result = issueService.updateAssignees(issueId, request.getAssignees());

        return ApiResponse.success(result);
    }
}
