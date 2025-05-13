package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.aws.S3FileService;
import codesquad.team4.issuetracker.exception.FileUploadException;
import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {
    private static final String ISSUE_DIRECTORY = "issues/";
    public static final String FILE_UPLOAD_FAILED = "파일 업로드에 실패했습니다.";

    private final IssueService issueService;
    private final IssueCountService issueCountService;
    private final S3FileService s3FileService;

    @GetMapping("")
    public ResponseEntity<IssueResponseDto.IssueListDto> showIssueList(@RequestParam(name = "is_open") boolean isOpen, Pageable pageable) {

        IssueResponseDto.IssueListDto issues = issueService.getIssues(isOpen, pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(issues);
    }

    @GetMapping("/count")
    public ResponseEntity<IssueCountDto> showIssueCount() {
        IssueCountDto result = issueCountService.getIssueCounts();
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<IssueResponseDto.CreateIssueDto> createIssue(
            @RequestPart("issue") IssueRequestDto.CreateIssueDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        String uploadUrl;

        try {
            uploadUrl = s3FileService.uploadFile(file, ISSUE_DIRECTORY);
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().body(
                    IssueResponseDto.CreateIssueDto.builder()
                    .message(FILE_UPLOAD_FAILED)
                    .build());
        }

        IssueResponseDto.CreateIssueDto result = issueService.createIssue(request, uploadUrl);
        return ResponseEntity.ok(result);
    }
}
