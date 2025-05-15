package codesquad.team01.issuetracker.user.dto;

public record UserResponse(
        Long id,
        String username,
        String profileImageUrl
) {
}
