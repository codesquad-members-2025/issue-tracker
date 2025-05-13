package CodeSquad.IssueTracker.home;

import CodeSquad.IssueTracker.home.dto.HomeResponseDto;
import CodeSquad.IssueTracker.issue.Issue;
import CodeSquad.IssueTracker.issue.IssueService;
import CodeSquad.IssueTracker.label.LabelService;
import CodeSquad.IssueTracker.milestone.MilestoneService;
import CodeSquad.IssueTracker.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public HomeResponseDto home() {
        return homeService.getHomeData();
    }


}
