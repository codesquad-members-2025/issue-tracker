package elbin_bank.issue_tracker.issue.application.query.dto;

public record MilestoneDto(Long id,
                           String name,
                           long progressRate) {
}
