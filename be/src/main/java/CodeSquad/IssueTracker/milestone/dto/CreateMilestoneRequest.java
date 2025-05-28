package CodeSquad.IssueTracker.milestone.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMilestoneRequest {

    private String name;
    private String description;
    private LocalDateTime endDate;

}
