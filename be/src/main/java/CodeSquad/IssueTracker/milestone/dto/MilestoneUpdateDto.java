package CodeSquad.IssueTracker.milestone.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MilestoneUpdateDto {
    private String name;
    private String description;
    private LocalDateTime endDate;
    private Boolean isOpen;
}