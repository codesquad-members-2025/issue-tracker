package codesquad.team01.issuetracker.issue.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;

@Builder
@Table("issue_assignee")
public class IssueAssignee {
	@Id
	private IssueAssigneeId id;

	public static IssueAssignee of(Integer issueId, Integer assigneeId) {
		return IssueAssignee.builder()
			.id(new IssueAssigneeId(issueId, assigneeId))
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		IssueAssignee that = (IssueAssignee)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
