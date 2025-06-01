package CodeSquad.IssueTracker.home;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.home.dto.IssueFilterCondition;
import CodeSquad.IssueTracker.home.dto.HomeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home")
    public BaseResponseDto<HomeResponseDto> home(@RequestParam(defaultValue = "1") int page, @ModelAttribute IssueFilterCondition condition) {
        return BaseResponseDto.success("이슈목록을 성공적으로 불러왔습니다.",
                homeService.getHomeData(page, condition));
    }
}
