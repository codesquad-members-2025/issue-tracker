package CodeSquad.IssueTracker.home;

import CodeSquad.IssueTracker.issue.Issue;
import CodeSquad.IssueTracker.issue.IssueService;
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

    private final IssueService issueService;

    @GetMapping("/")
    public String home(Model model){
        Iterable<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);
        return "home/index";
    }


}
