package codesquad.team01.issuetracker.label.dto;

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
	public record FilterListItemResponse(
		int id,
		String name,
		String color,
		String textColor
	) {
	}

	@Builder
	public record ListItemResponse(
		int id,
		String name,
		String description,
		String color,
		String textColor
	) {
		public static ListItemResponse from(LabelRow row) {
			return new ListItemResponse(
				row.id(),
				row.name(),
				row.description(),
				row.color(),
				row.textColor()
			);
		}
	}

	public record ListResponse(
		int totalCount,
		List<ListItemResponse> labels
	) {
	}

	// 이슈 레이블 행 DTO
	@Builder
	public record IssueLabelRow(
		int issueId,
		int labelId,
		String labelName,
		String labelColor,
		LabelTextColor labelTextColor
	) {
	}

	@Builder
	public record LabelFilterResponse(
		int id,
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

	public record LabelRow(
		int id,
		String name,
		String description,
		String color,
		String textColor
	) {
	}
}
