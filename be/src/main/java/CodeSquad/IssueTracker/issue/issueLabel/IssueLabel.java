package CodeSquad.IssueTracker.issue.issueLabel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("issue_label")
public class IssueLabel {
    @Id
    private Long issueLabelId;
    private Long issueId;
    private Long labelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}