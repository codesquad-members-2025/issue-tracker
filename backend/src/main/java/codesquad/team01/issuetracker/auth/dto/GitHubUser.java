package codesquad.team01.issuetracker.auth.dto;

public record GitHubUser(
        Long id,
        String githubId,
        String avatarUrl
) {
}
