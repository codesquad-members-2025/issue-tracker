package elbin_bank.issue_tracker.milestone.infrastructure.query.projection;

public record MilestoneProjection(
        long id,
        String title,
        long totalIssueCount,
        long closedIssueCount
) {
}
