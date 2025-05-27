package elbin_bank.issue_tracker.issue.application.query;

import elbin_bank.issue_tracker.issue.application.query.dsl.DslParser;
import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.application.query.dto.IssueDetailResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.application.query.mapper.IssueDtoMapper;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.label.application.query.repository.LabelQueryRepository;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IssueQueryService {

    private final IssueQueryRepository issueQueryRepository;
    private final LabelQueryRepository labelQueryRepository;
    private final IssueDtoMapper issueDtoMapper;

    @Transactional(readOnly = true)
    public IssuesResponseDto getFilteredIssues(String dsl) {
        FilterCriteria crit = DslParser.parse(dsl);
        List<IssueProjection> issues = issueQueryRepository.findIssues(crit);

        List<Long> ids = issues.stream().map(IssueProjection::id).toList();

        Map<Long, List<LabelProjection>> labels = labelQueryRepository.findByIssueIds(ids);
        Map<Long, List<String>> assigneeNames = issueQueryRepository.findAssigneeNamesByIssueIds(ids);

        IssueCountProjection issueCount = issueQueryRepository.countIssueOpenAndClosed();

        return issueDtoMapper.toIssuesResponseDto(issues, labels, assigneeNames, issueCount);
    }

    @Transactional(readOnly = true)
    public IssueDetailResponseDto getIssue(long id) {
        IssueDetailProjection issue = issueQueryRepository.findById(id)
                .orElseThrow(() -> new IssueDetailNotFoundException(id));
        List<LabelProjection> labels = labelQueryRepository.findByIssueIds(List.of(id)).getOrDefault(id, List.of());
        List<UserInfoProjection> assignees = issueQueryRepository.findAssigneesByIssueId(id);

        return issueDtoMapper.toIssueDetailDto(issue, labels, assignees);
    }

}
