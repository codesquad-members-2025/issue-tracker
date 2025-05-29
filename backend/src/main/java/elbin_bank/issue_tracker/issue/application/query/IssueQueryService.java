package elbin_bank.issue_tracker.issue.application.query;

import elbin_bank.issue_tracker.issue.application.query.dsl.DslParser;
import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.application.query.dto.*;
import elbin_bank.issue_tracker.issue.application.query.mapper.IssueDtoMapper;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.label.application.query.repository.LabelQueryRepository;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.milestone.application.query.repository.MilestoneQueryRepository;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;
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
    private final MilestoneQueryRepository milestoneQueryRepository;
    private final IssueDtoMapper issueDtoMapper;

    @Transactional(readOnly = true)
    public IssuesResponseDto getFilteredIssues(String dsl) {
        IssueCountProjection issueCount = issueQueryRepository.countIssueOpenAndClosed();

        FilterCriteria crit = DslParser.parse(dsl);
        if (crit.isInvalidFilter()) {
            return issueDtoMapper.toIssuesResponseDto(List.of(), Map.of(), Map.of(), issueCount);
        }

        List<IssueProjection> issues = issueQueryRepository.findIssues(crit);
        List<Long> ids = issues.stream().map(IssueProjection::id).toList();
        Map<Long, List<String>> assigneeNames = issueQueryRepository.findAssigneeNamesByIssueIds(ids);
        Map<Long, List<LabelProjection>> labels = labelQueryRepository.findByIssueIds(ids);

        return issueDtoMapper.toIssuesResponseDto(issues, assigneeNames, labels, issueCount);
    }

    @Transactional(readOnly = true)
    public IssueDetailResponseDto getIssue(long id) {
        IssueDetailProjection issue = issueQueryRepository.findById(id)
                .orElseThrow(() -> new IssueDetailNotFoundException(id));

        return issueDtoMapper.toIssueDetailDto(issue);
    }

    @Transactional(readOnly = true)
    public LabelsResponseDto getLabelsRelatedToIssue(long id) {
        List<LabelProjection> labels = labelQueryRepository.findByIssueIds(List.of(id)).getOrDefault(id, List.of());

        return issueDtoMapper.toLabelsResponseDto(labels);
    }

    @Transactional(readOnly = true)
    public AssigneesResponseDto getAssigneesRelatedToIssue(long id) {
        List<UserInfoProjection> assigneeNames = issueQueryRepository.findAssigneeByIssueId(id);

        return issueDtoMapper.toAssigneesResponseDto(assigneeNames);
    }

    @Transactional(readOnly = true)
    public MilestoneResponseDto getMilestoneForIssue(long id) {
        MilestoneProjection milestone = milestoneQueryRepository.findByIssueId(id)
                .orElseThrow(() -> new IssueDetailNotFoundException(id));

        return issueDtoMapper.toMilestoneResponseDto(milestone);
    }

}
