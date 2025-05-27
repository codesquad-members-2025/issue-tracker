package codesquad.team4.issuetracker.count;

import codesquad.team4.issuetracker.count.dto.IssueCountDto;
import codesquad.team4.issuetracker.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CountController {
    private final CountService countService;

    @GetMapping("api/issues/count")
    public ApiResponse<IssueCountDto> showIssueCount() {
        IssueCountDto result = countService.getIssueCounts();
        return ApiResponse.success(result);
    }

}
