package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.issue.dto.IssueCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/write")
    public Issue writeIssue(@RequestBody IssueCreateRequest request){
        return issueService.createIssue(request);
    }


}
