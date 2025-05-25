package elbin_bank.issue_tracker.issue.application.query;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueDetailResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.application.query.mapper.IssueDtoMapper;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategyContext;
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
    private final FilterStrategyContext filterStrategyContext;

    @Transactional(readOnly = true)
    public IssuesResponseDto getFilteredIssues(String q) {
        String rawQuery = normalize(q);
//        FilterStrategyContext.SqlAndParams sql = filterStrategyContext.buildSql(rawQuery);
        List<IssueProjection> issues = issueQueryRepository.findIssues(rawQuery);

        List<Long> ids = issues.stream().map(IssueProjection::id).toList();

        Map<Long, List<LabelProjection>> labels = labelQueryRepository.findByIssueIds(ids);
        Map<Long, List<String>> assigneeNames = issueQueryRepository.findAssigneeNamesByIssueIds(ids);

        IssueCountProjection issueCount = issueQueryRepository.countIssueOpenAndClosed();

        return issueDtoMapper.toIssuesResponseDto(issues, labels, assigneeNames, issueCount);
    }

    private String normalize(String raw) {
        if (raw == null || raw.isBlank()) {
            // 아무것도 없으면 열린 이슈만
            return "state:open";
        }
        String q = raw.trim();
        // 명시적으로 'is:issue'만 주면 모든 이슈
        if ("is:issue".equals(q)) {
            return "";
        }
        return q;
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
