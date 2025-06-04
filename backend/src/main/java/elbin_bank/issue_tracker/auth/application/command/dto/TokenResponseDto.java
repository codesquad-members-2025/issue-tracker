package elbin_bank.issue_tracker.auth.application.command.dto;

public record TokenResponseDto(
        String accessToken,
        String tokenType
) {
}
