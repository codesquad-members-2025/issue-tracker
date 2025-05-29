package elbin_bank.issue_tracker.milestone.application.query.dto;

public record MilestoneDto(
        long id,
        String title,
        int progressRate
) {
}
