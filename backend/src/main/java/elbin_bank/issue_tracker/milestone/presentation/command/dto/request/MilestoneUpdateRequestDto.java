package elbin_bank.issue_tracker.milestone.presentation.command.dto.request;

public record MilestoneUpdateRequestDto(String title, String expiredAt, String description) {
}
