package elbin_bank.issue_tracker.milestone.presentation.command.dto.request;

public record MilestoneUpdateStateRequestDto(
        boolean targetClosed
) {
}
