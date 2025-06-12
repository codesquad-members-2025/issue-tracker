package elbin_bank.issue_tracker.milestone.application.query.dto;

public record MilestoneDto(
        long id,
        String title,
        String description,
        String expiredAt,
        int progressRate,
        boolean isClosed,
        long totalIssueCount,
        long closedIssueCount
) {
}
