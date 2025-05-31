package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import CodeSquad.IssueTracker.issue.dto.IssueDetailResponse;
import CodeSquad.IssueTracker.issue.dto.IssueUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import static CodeSquad.IssueTracker.global.message.SuccessMessage.ISSUE_DETAIL_FETCH_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IssueDetailResponse createIssue(
            @RequestPart("data") @Validated IssueCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            HttpServletRequest httpRequest
    ) throws IOException {
        String loginId = httpRequest.getAttribute("loginId").toString();
        log.info("Creating new issue with login id {}", loginId);
        return issueService.createIssue(request, files, loginId);
    }


    @GetMapping("/{issueId}")
    public BaseResponseDto<IssueDetailResponse> getIssueDetailInfo(@PathVariable Long issueId) {
        Issue byIdIssue = issueService.findById(issueId).get();
        return BaseResponseDto.success(ISSUE_DETAIL_FETCH_SUCCESS.getMessage(), issueService.toDetailResponse(byIdIssue));
    }

    @PatchMapping("/{issueId}")
    public IssueDetailResponse updateIssue(@PathVariable Long issueId, @RequestBody IssueUpdateDto updateParam) {
        return issueService.update(issueId, updateParam);
    }


}
