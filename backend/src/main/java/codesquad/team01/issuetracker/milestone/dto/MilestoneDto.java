package codesquad.team01.issuetracker.milestone.dto;

import java.util.List;

import lombok.Builder;

public class MilestoneDto {
	private MilestoneDto() {
	}

	/**
	 * 응답 DTO
	 */
	// 이슈 목록 - 마일스톤 응답 DTO
	@Builder
	public record ListItemResponse(
		Integer id,

		String title
	) {
	}

	@Builder
	public record MilestoneFilterResponse(
		Integer id,

		String title
	) {
	}

	@Builder
	public record MilestoneFilterListResponse(
		int totalCount,
		List<MilestoneFilterResponse> milestones
	) {
	}
}
