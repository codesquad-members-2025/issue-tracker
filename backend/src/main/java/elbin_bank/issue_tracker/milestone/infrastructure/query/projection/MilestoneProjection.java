package elbin_bank.issue_tracker.milestone.infrastructure.query.projection;

public record MilestoneProjection(
        long id,
        String title,
        String description,
        String expiredAt,
        boolean isClosed,
        long totalIssueCount,
        long closedIssueCount
) {
}
