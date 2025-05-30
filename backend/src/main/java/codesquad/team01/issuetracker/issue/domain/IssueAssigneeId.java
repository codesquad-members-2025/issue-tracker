package codesquad.team01.issuetracker.issue.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class IssueAssigneeId implements Serializable {
	private Integer issueId;
	private Integer assigneeId;
}
