package elbin_bank.issue_tracker.issue.application.query.dto;

import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;

import java.util.List;

public record IssuesResponseDto(List<IssueProjection> issues, long openCount, long closeCount) {
}
