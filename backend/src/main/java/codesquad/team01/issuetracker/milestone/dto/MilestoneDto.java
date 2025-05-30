package codesquad.team01.issuetracker.milestone.dto;

import java.time.LocalDate;
import java.util.List;

import codesquad.team01.issuetracker.milestone.domain.MilestoneState;
import lombok.Builder;

public class MilestoneDto {
	private MilestoneDto() {
	}

	/*
	 * 응답 DTO
	 */
	// 이슈 목록 - 마일스톤 응답 DTO
	@Builder
	public record ListItemResponse(
		int id,
		String title
	) {
	}

	// 필터용 마일스톤 응답 DTO
	@Builder
	public record MilestoneFilterResponse(
		int id,
		String title
	) {
	}

	// 필터용 마일스톤 목록 응답 DTO
	@Builder
	public record MilestoneFilterListResponse(
		int totalCount,
		List<MilestoneFilterResponse> milestones
	) {
	}

	// 마일스톤 목록 조회용 응답 DTO
	public record

	// 목록의 구성요소 DTO
	public record ListResponse(
		int totalCount,
		List<>
	)

	// DB 조회용 DTO
	public record MilestoneRow(
		int id,
		String title,
		String description,
		LocalDate dueDate,
		MilestoneState state
	) {
	}
}
