package codesquad.team01.issuetracker.auth.dto;

// 서비스 관점에서 로그인에 필요한 정보를 담은 dto
// jwt, id, 프로필 정보 등
public record LoginResponse(
        String loginId,
        String avatarUrl,
        String email
) {
}
