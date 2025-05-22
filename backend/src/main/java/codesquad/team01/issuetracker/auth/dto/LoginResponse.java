package codesquad.team01.issuetracker.auth.dto;

public record LoginResponse(
	Long id, //int로 변환
	String loginId, //지워질 필드
	String avatarUrl //지워질 필드
) {
}
