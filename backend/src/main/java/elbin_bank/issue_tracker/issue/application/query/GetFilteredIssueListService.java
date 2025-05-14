package elbin_bank.issue_tracker.issue.application.query;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueSummaryDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueRepository;
import elbin_bank.issue_tracker.issue.domain.IssueStatus;
import elbin_bank.issue_tracker.label.application.query.dto.LabelsDto;
import elbin_bank.issue_tracker.label.domain.Label;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Service
public class GetFilteredIssueListService {

    private final IssueRepository issueRepository;

    public GetFilteredIssueListService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    private static final Map<Long, String> userNames = Map.of(
            0L, "elbin",
            1L, "glad",
            2L, "wanja",
            3L, "wanja",
            4L, "gd"
    );

    @Transactional(readOnly = true)
    public IssuesResponseDto find(String q) {
        FilterCriteria crit = FilterConditionParser.parse(q);
        List<Issue> issues = issueRepository.findByFilter(crit.isClosed());
        if (issues.isEmpty()) {
            return new IssuesResponseDto(List.of(), 0L, 0L);
        }

        List<Long> issueIds = issues.stream().map(Issue::getId).toList();
        List<Long> authorIds = issues.stream().map(Issue::getAuthorId).distinct().toList();

//        Map<Long, String> authors = userRepo.findNicknamesByIds(authorIds); @todo
        Map<Long, List<Label>> labelsMap = issueRepository.findLabelsByIssueIds(issueIds);
        Map<Long, List<String>> assigneesMap = issueRepository.findAssigneesByIssueIds(issueIds);

        List<IssueSummaryDto> dtos = issues.stream().map(i ->
                        new IssueSummaryDto(
                                i.getId(),
//                        authors.get(i.getAuthorId()), @todo
                                userNames.get(i.getAuthorId()),
                                i.getTitle(),
                                labelsMap.getOrDefault(i.getId(), emptyList())
                                        .stream().map(label -> new LabelsDto(label.getId(), label.getName(), label.getDescription(), label.getColor())
                                        ).toList(),
                                i.isClosed(),
                                i.getCreatedAt(),
                                i.getUpdatedAt(),
                                assigneesMap.getOrDefault(i.getId(), emptyList()),
                                "hello wanja" // @todo
                        )
        ).toList();

        long openCount = issueRepository.countByStatus(IssueStatus.OPEN);
        long closeCount = issueRepository.countByStatus(IssueStatus.CLOSED);

        return new IssuesResponseDto(dtos, openCount, closeCount);
    }

}
