package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
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

    @PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Issue createIssue(
            @RequestPart("data") IssueCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        return issueService.createIssue(request,files);
    } //지금 issue에는 imageUrl이 없음 ResponseEntity<Issue> 로 변환도 고려해봐야함 아님 response DTO만들자


}
