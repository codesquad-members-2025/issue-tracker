package codesquad.team01.issuetracker.issue.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class IssueLabelId implements Serializable {
	private Integer issueId;
	private Integer labelId;
}
