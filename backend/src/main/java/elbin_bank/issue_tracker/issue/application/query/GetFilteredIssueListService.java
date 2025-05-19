package elbin_bank.issue_tracker.issue.application.query;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueSummaryDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueRepository;
import elbin_bank.issue_tracker.issue.domain.IssueStatus;
import elbin_bank.issue_tracker.label.application.query.dto.LabelsDto;
import elbin_bank.issue_tracker.label.domain.Label;
import elbin_bank.issue_tracker.milestone.domain.MilestoneRepository;
import elbin_bank.issue_tracker.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GetFilteredIssueListService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final MilestoneRepository milestoneRepository;

    public GetFilteredIssueListService(IssueRepository issueRepository, UserRepository userRepository, MilestoneRepository milestoneRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.milestoneRepository = milestoneRepository;
    }

    @Transactional(readOnly = true)
    public IssuesResponseDto find(String q) {
        FilterCriteria crit = FilterConditionParser.parse(q);
        List<Issue> issues = issueRepository.findByFilter(crit.isClosed());
        if (issues.isEmpty()) {
            return new IssuesResponseDto(List.of(), 0L, 0L);
        }

        List<Long> issueIds = issues.stream().map(Issue::getId).toList();
        List<Long> authorIds = issues.stream().map(Issue::getAuthorId).distinct().toList();
        List<Long> milestoneIds = issues.stream().map(Issue::getMilestoneId).toList();

        Map<Long, String> authors = userRepository.findNickNamesByIds(authorIds);
        Map<Long, List<Label>> labelsMap = issueRepository.findLabelsByIssueIds(issueIds);
        Map<Long, List<String>> assigneesMap = issueRepository.findAssigneesByIssueIds(issueIds);
        Map<Long, String> milestoneNames = milestoneRepository.findTitlesByIds(milestoneIds);

        List<IssueSummaryDto> dtos = issues.stream().map(i ->
                new IssueSummaryDto(
                        i.getId(),
                        authors.get(i.getAuthorId()),
                        i.getTitle(),
                        labelsMap.getOrDefault(i.getId(), Collections.emptyList())
                                .stream().map(label -> new LabelsDto(label.getId(), label.getName(), label.getDescription(), label.getColor())
                                ).toList(),
                        i.isClosed(),
                        i.getCreatedAt(),
                        i.getUpdatedAt(),
                        assigneesMap.getOrDefault(i.getId(), Collections.emptyList()),
                        milestoneNames.getOrDefault(i.getMilestoneId(), null)
                )
        ).toList();

        long openCount = issueRepository.countByStatus(IssueStatus.OPEN);
        long closeCount = issueRepository.countByStatus(IssueStatus.CLOSED);

        return new IssuesResponseDto(dtos, openCount, closeCount);
    }

}
