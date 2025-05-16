package codesquad.team4.issuetracker.milestone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MilestoneDto {

    @AllArgsConstructor
    @Getter
    @Builder
    public static class MilestoneInfo {
        private Long id;
        private String name;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class MilestoneFilter {
        private List<MilestoneInfo> milestones;
        private int count;
    }
}
