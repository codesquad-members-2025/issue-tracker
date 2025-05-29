package elbin_bank.issue_tracker.issue.application.query.dto;

public record MilestoneResponseDto(long id,
                                   String name,
                                   int progressRate) {
}
