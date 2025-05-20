package codesquad.team01.issuetracker.user.dto;

import lombok.Builder;

public class UserDto {

	private UserDto() {
	}

	private static final String DEFAULT_PROFILE_IMAGE_URL = "/images/default-profile.png";

	/**
	 * 응답 DTO
	 */
	// 이슈 목록 - 작성자 응답 DTO
	@Builder
	public record WriterResponse(
		Integer id,
		String username,
		String profileImageUrl
	) {
		public String profileImageUrl() {
			return profileImageUrl != null ? profileImageUrl : DEFAULT_PROFILE_IMAGE_URL;
		}
	}

	// 담당자 응답 DTO
	@Builder
	public record AssigneeResponse(
		Integer id,
		String profileImageUrl
	) {
		public String profileImageUrl() {
			return profileImageUrl != null ? profileImageUrl : DEFAULT_PROFILE_IMAGE_URL;
		}
	}

	/**
	 * DB 조회용 DTO
	 */
	// 이슈 담당자 행 DTO
	@Builder
	public record IssueAssigneeRow(
		Integer issueId,
		Integer assigneeId,
		String assigneeProfileImageUrl
	) {
	}
}
