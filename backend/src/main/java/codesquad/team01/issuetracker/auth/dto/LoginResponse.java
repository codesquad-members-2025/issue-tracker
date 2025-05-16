package codesquad.team01.issuetracker.auth.dto;

public record LoginResponse(
        Long id,
        String loginId,
        String avatarUrl
) {
}
