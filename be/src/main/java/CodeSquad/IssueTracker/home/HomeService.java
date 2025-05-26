package CodeSquad.IssueTracker.home;

import CodeSquad.IssueTracker.home.dto.IssueFilterRequestDto;
import CodeSquad.IssueTracker.home.dto.HomeResponseDto;
import CodeSquad.IssueTracker.home.dto.MetaData;
import CodeSquad.IssueTracker.issue.IssueService;
import CodeSquad.IssueTracker.label.LabelService;
import CodeSquad.IssueTracker.milestone.MilestoneService;
import CodeSquad.IssueTracker.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final IssueService issueService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;
    private final UserService userService;

    public HomeResponseDto getHomeData(int currentPage, IssueFilterRequestDto filterRequestDto) {
        int openIssueNumber = issueService.countIssuesByOpenStatus(true, filterRequestDto);
        int closeIssueNumber = issueService.countIssuesByOpenStatus(false, filterRequestDto);
        int maxPage = issueService.getIssueMaxPage(filterRequestDto);
        return new HomeResponseDto(
                issueService.findIssuesByFilter(currentPage, filterRequestDto),
                userService.findAllUserDetails(),
                labelService.findAll(),
                milestoneService.findAll(),
                new MetaData(currentPage, maxPage, openIssueNumber, closeIssueNumber)
        );
    }
}
