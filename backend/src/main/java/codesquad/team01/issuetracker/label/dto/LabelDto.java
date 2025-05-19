package codesquad.team01.issuetracker.label.dto;

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
}
