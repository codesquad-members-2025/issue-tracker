package codesquad.team4.issuetracker.milestone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class MilestoneFilterDto {
    private List<MilestoneInfo> milestones;
    private int count;

    @AllArgsConstructor
    @Getter
    @Builder
    public static class MilestoneInfo {
        private Long id;
        private String name;
    }
}
