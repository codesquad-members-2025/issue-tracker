package elbin_bank.issue_tracker.auth.infrastructure.provider.dto;

public record OAuthUserInfoDto(
        String provider,
        Long providerId,
        String nickname,
        String avatarUrl
) {
}
