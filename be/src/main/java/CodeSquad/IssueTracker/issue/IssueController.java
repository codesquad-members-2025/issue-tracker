package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.comment.Comment;
import CodeSquad.IssueTracker.comment.CommentService;
import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import CodeSquad.IssueTracker.issue.dto.IssueDetailResponse;
import CodeSquad.IssueTracker.issue.dto.IssueUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final CommentService commentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IssueDetailResponse createIssue(
            @RequestPart("data") @Validated IssueCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            HttpServletRequest httpRequest
    ) throws IOException {
        String loginId = httpRequest.getAttribute("loginId").toString();
        return issueService.createIssue(request,files,loginId);
    }

    @GetMapping("/{issueId}")
    public IssueDetailResponse getIssueDetailInfo(@PathVariable Long issueId) {
        Issue byIdIssue = issueService.findById(issueId).get();
        return issueService.toDetailResponse(byIdIssue);
    }

    @PatchMapping("{issueId}")
    public void updateIssue(@PathVariable Long issueId, @RequestBody IssueUpdateDto updateParam) {
        issueService.update(issueId, updateParam);
    }




}
