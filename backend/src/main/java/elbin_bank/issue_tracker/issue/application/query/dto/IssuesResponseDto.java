package elbin_bank.issue_tracker.issue.application.query.dto;

import java.util.List;

public record IssuesResponseDto(List<IssueDto> issues, long openCount, long closeCount) {
}
