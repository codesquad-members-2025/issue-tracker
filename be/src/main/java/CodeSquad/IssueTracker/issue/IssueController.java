package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.comment.Comment;
import CodeSquad.IssueTracker.comment.CommentService;
import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import CodeSquad.IssueTracker.issue.dto.IssueUpdateDto;
import CodeSquad.IssueTracker.issue.dto.IssueDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final CommentService commentService;

    @PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Issue createIssue(
            @RequestPart("data") IssueCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        return issueService.createIssue(request,files);
    } //지금 issue에는 imageUrl이 없음 ResponseEntity<Issue> 로 변환도 고려해봐야함 아님 response DTO만들자

    @GetMapping("/{issueId}")
    public IssueDetailResponse getIssueParticleInfo(@PathVariable Long issueId) {
        Issue byIdIssue = issueService.findById(issueId).get();
        List<Comment> byIssueIdComments = commentService.findByIssueId(issueId);
        IssueDetailResponse response = new IssueDetailResponse();
        response.setIssue(byIdIssue);
        response.setComments(byIssueIdComments);
        return response;
    }

    @PostMapping("{issueId}/update")
    public void updateIssue(@PathVariable Long issueId, @RequestBody IssueUpdateDto updateParam) {
        issueService.update(issueId, updateParam);
    }




}
