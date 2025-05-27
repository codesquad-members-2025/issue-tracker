package elbin_bank.issue_tracker.milestone.application.query.dto;

public record MilestoneMainResponseDto(
        long id,
        String title,
        Integer progressRate
) {
}
