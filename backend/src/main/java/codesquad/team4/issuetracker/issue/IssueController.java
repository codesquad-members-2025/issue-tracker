package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto.IssueFilterParamDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.ApiMessageDto;
import codesquad.team4.issuetracker.response.ApiResponse;
import codesquad.team4.issuetracker.util.IssueFilteringParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public ApiResponse<IssueResponseDto.IssueListDto> showIssueList(
        @RequestParam(required = false, name = "q") String queryString, Pageable pageable) {

        IssueFilterParamDto filter = IssueFilteringParser.parseFilterCondition(queryString);
        IssueResponseDto.IssueListDto issues = issueService.getIssues(filter, pageable.getPageNumber(), pageable.getPageSize());

        return ApiResponse.success(issues);
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
            @RequestBody @Valid IssueRequestDto.BulkUpdateIssueStatusDto requestDto) {

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

    @PutMapping("/{issue-id}/labels")
    public ApiResponse<IssueResponseDto.ApiMessageDto> updateIssueLabels(@PathVariable("issue-id") Long issueId, @RequestBody IssueRequestDto.IssueLabelsUpdateDto request) {

        ApiMessageDto result = issueService.updateLabels(issueId, request.getLabels());

        return ApiResponse.success(result);
    }

    @PutMapping("/{issue-id}/assignees")
    public ApiResponse<IssueResponseDto.ApiMessageDto> updateIssueAssignees(@PathVariable("issue-id") Long issueId, @RequestBody IssueRequestDto.IssueAssigneeUpdateDto request) {

        ApiMessageDto result = issueService.updateAssignees(issueId, request.getAssignees());

        return ApiResponse.success(result);
    }

    @DeleteMapping("/{issue-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssue(@PathVariable("issue-id") Long issueId) {
        issueService.deleteIssue(issueId);
    }
}
