package CodeSquad.IssueTracker.issue;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueUpdateDto {

    private String title;
    private Boolean isOpen;
    private LocalDateTime timestamp;
    private Long assigneeId;
    private Long milestoneId;

}
