package codesquad.team01.issuetracker.milestone.dto;

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
            Long id,
            String title
    ) {
    }
}
