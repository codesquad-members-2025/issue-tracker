package codesquad.team01.issuetracker.auth.dto;

public record GitHubEmail(
	String email,
	boolean primary,
	boolean verified,
	String visibility
) {
}
