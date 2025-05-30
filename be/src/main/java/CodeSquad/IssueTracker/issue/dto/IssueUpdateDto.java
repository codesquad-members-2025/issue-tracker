package CodeSquad.IssueTracker.issue.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IssueUpdateDto {

    private String title;
    private String content;
    private Boolean isOpen;
    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;

}
