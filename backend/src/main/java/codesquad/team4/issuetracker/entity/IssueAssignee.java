package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("issue_assignee")
@AllArgsConstructor
@Getter
@Builder
public class IssueAssignee {
    @Id
    @Column("issue_assignee_id")
    private Long id;

    @Column("issue_id")
    private Long issueId;

    @Column("assignee_id")
    private Long assigneeId;

    @Column("created_at")
    private LocalDateTime createdAt;
}
