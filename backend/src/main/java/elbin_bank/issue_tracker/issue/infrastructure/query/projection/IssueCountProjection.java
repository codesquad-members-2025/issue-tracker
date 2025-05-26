package elbin_bank.issue_tracker.issue.infrastructure.query.projection;

public record IssueCountProjection(long openCount,
                                   long closedCount) {
}
