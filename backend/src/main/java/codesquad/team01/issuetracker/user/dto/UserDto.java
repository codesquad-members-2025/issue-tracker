package codesquad.team01.issuetracker.user.dto;

import java.util.List;

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
		// 처음엔 작성자 응답 DTO로 생성했지만
		// 현재 담당자, 작성자 필터 db 조회용 dto, 응답용 dto의 역할도 하고 있음
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

	@Builder
	public record UserFilterListResponse(
		int totalCount,
		List<WriterResponse> users // WriterResponse와 같이 데이터를 필요로 해서 임시로 넣음
		// 이럴 경우 하나 더 만들지? 이름을 범용적으로 바꿀지?
	) {
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
