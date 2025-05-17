package CodeSquad.IssueTracker.issue.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueUpdateDto {

    private String title;
    private String content;
    private Boolean isOpen;
    private LocalDateTime timestamp;
    private Long assigneeId;
    private Long milestoneId;

}
