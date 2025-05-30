package CodeSquad.IssueTracker.issueAssignee;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("issue_assignee")
public class IssueAssignee {
    @Id
    private Long issueAssigneeId;
    private Long issueId;
    private Long assigneeId;
    private LocalDateTime lastModifiedAt;
}
