package codesquad.team01.issuetracker.auth.dto;

import jakarta.annotation.Nullable;

public class AuthDto {

	private AuthDto() {

	}

	public record GitHubUser(
		long id,
		String githubId,
		String avatarUrl,
		String email
	) {
	}

	public record GitHubEmail(
		String email,
		boolean primary,
		boolean verified,
		String visibility
	) {
	}

	public record LoginResponse(
		int id,
		@Nullable String email
	) {
	}
}