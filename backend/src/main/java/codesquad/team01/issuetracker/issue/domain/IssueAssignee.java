package codesquad.team01.issuetracker.issue.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;

@Builder
@Table("issue_label")
public class IssueLabel {
	@Id
	private IssueLabelId id;

	public static IssueLabel of(Integer issueId, Integer labelId) {
		return IssueLabel.builder()
			.id(new IssueLabelId(issueId, labelId))
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		IssueLabel that = (IssueLabel)o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
