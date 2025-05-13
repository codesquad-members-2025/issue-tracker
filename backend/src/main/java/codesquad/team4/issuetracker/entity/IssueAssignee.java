package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue_assignee")
@AllArgsConstructor
@Getter
@Builder
public class IssueAssignee {
    @Id
    private Long id;
    private Long issueId;
    private Long assigneeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
