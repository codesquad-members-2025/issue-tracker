package CodeSquad.IssueTracker.home;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.home.dto.IssueFilterCondition;
import CodeSquad.IssueTracker.home.dto.HomeResponseDto;
import CodeSquad.IssueTracker.issue.IssueService;
import CodeSquad.IssueTracker.issue.dto.IssueStatusUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final IssueService issueService;

    @GetMapping("/home")
    public BaseResponseDto<HomeResponseDto> home(@RequestParam(defaultValue = "1") int page, @ModelAttribute IssueFilterCondition condition) {
        return BaseResponseDto.success("이슈목록을 성공적으로 불러왔습니다.",
                homeService.getHomeData(page, condition));
    }

    @PatchMapping("toggleStatus")
    public BaseResponseDto home(@RequestBody IssueStatusUpdateRequest condition) {
        issueService.updateIssueOpenState(condition);
        return BaseResponseDto.success("이슈 상태를 성공적으로 변경했습니다.", null);
    }
}
