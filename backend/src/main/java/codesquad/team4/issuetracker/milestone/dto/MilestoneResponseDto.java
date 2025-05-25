package codesquad.team4.issuetracker.milestone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MilestoneResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class MilestoneInfo {
        private Long id;
        private String title;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class MilestoneFilter {
        private List<MilestoneInfo> milestones;
        private int count;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class MilestoneCountDto {
        private Integer openCount;
        private Integer closedCount;
    }
}
