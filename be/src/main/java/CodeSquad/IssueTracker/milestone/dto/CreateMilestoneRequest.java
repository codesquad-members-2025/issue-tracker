package CodeSquad.IssueTracker.milestone.dto;

import CodeSquad.IssueTracker.milestone.Milestone;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMilestoneRequest {

    private String name;
    private String description;
    private LocalDateTime endDate;

    public Milestone toEntity(){
        return new Milestone(
                name,description,endDate,true,LocalDateTime.now()
        );
    }
}
