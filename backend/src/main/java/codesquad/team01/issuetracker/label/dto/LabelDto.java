package codesquad.team01.issuetracker.label.dto;

import java.util.List;

import java.time.LocalDateTime;
import java.util.List;

import codesquad.team01.issuetracker.label.domain.LabelTextColor;
import lombok.Builder;

public class LabelDto {
	private LabelDto() {
	}

	/*
	 * 응답 DTO
	 */
	// 이슈 목록 - 레이블 응답 DTO
	@Builder
	public record ListItemResponse(
		Integer id,

		String name,
		String description,
		String color,
		String textColor
	) {
	}

	public record ListResponse(
		List<ListItemResponse> labels
	) {
	}

	// 이슈 레이블 행 DTO
	@Builder
	public record IssueLabelRow(
		Integer issueId,
		Integer labelId,
		String labelName,
		String labelColor,
		LabelTextColor labelTextColor
	) {
	}

	@Builder
	public record LabelFilterResponse(
		Integer id,

		String name,
		String color
	) {
	}

	@Builder
	public record LabelFilterListResponse(
		int totalCount,
		List<LabelFilterResponse> labels
	) {
	}

	public record LabelListItem(
		Integer id,
		String name,
		String description,
		String color,
		String textColor,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
	) {
	}
}
