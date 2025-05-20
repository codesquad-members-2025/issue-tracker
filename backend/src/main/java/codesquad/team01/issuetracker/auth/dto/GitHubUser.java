package codesquad.team01.issuetracker.auth.dto;

// 깃헙이 주는 사용자의 정보를 담는 dto
public record GitHubUser(
        long id,
        String githubId,
        String avatarUrl,
        String email
) {
}
