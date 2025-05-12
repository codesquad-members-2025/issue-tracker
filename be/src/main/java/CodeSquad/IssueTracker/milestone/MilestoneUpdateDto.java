package CodeSquad.IssueTracker.milestone;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MilestoneUpdateDto {
    private String name;
    private String description;
    private LocalDateTime endDate;
    private Boolean isOpen;
}