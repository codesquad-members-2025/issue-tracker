package elbin_bank.issue_tracker.issue.application.query.dto;

import java.util.List;

public record IssuesResponseDto(List<IssueSummaryDto> issues, long openCount, long closeCount) {
}
