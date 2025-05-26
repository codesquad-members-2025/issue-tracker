package elbin_bank.issue_tracker.issue.application.query;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
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

    @Transactional(readOnly = true)
    public IssuesResponseDto getFilteredIssues(String q) {
        String rawQuery = normalize(q);
        List<IssueProjection> issues = issueQueryRepository.findIssues(rawQuery);

        List<Long> ids = issues.stream().map(IssueProjection::id).toList();

        Map<Long, List<LabelProjection>> labels = labelQueryRepository.findByIssueIds(ids);
        Map<Long, List<String>> assigneeNames = issueQueryRepository.findAssigneesByIssueIds(ids);

        IssueCountProjection issueCount = issueQueryRepository.countIssueOpenAndClosed();

        return new IssuesResponseDto(issues.stream()
                .map(b -> new IssueDto(
                        b.id(), b.author(), b.title(),
                        labels.getOrDefault(b.id(), List.of()),
                        b.isClosed(), b.createdAt(), b.updatedAt(),
                        assigneeNames.getOrDefault(b.id(), List.of()),
                        b.milestone()
                )).toList(),
                issueCount.openCount(),
                issueCount.closedCount());
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

}
