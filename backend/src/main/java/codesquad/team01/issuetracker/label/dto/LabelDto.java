package codesquad.team01.issuetracker.label.dto;

import codesquad.team01.issuetracker.label.domain.LabelTextColor;
import lombok.Builder;

public class LabelDto {
    private LabelDto() {
    }

    /**
     * 응답 DTO
     */
    // 이슈 목록 - 레이블 응답 DTO
    @Builder
    public record ListItemResponse(
            Long id,
            String name,
            String color,
            String textColor
    ) {
    }

    // 이슈 레이블 행 DTO
    @Builder
    public record IssueLabelRow(
            Long issueId,
            Long labelId,
            String labelName,
            String labelColor,
            LabelTextColor labelTextColor
    ) {
    }
}
