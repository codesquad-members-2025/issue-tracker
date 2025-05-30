package CodeSquad.IssueTracker.milestone.dto;

import lombok.Data;

import java.util.List;

@Data
public class MilestoneListResponse {
    private List<MilestoneResponse> milestones;
    private int openCount;
    private int closedCount;

    public MilestoneListResponse(List<MilestoneResponse> milestones, int openCount, int closedCount) {
        this.milestones = milestones;
        this.openCount = openCount;
        this.closedCount = closedCount;
    }
}
