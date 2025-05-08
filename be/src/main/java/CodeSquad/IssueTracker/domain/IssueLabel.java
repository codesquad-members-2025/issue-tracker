package CodeSquad.IssueTracker.domain;

import org.springframework.data.relational.core.mapping.Table;

@Table("issue_labels")
public class IssueLabel {
    private Long issueId;
    private Long labelId;
}
