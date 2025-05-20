package codesquad.team01.issuetracker.issue.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssueState {
	OPEN("open"),
	CLOSED("closed");

	private final String value;

	public static IssueState fromStateStr(String state) {
		for (IssueState issueState : IssueState.values()) {
			if (issueState.value.equalsIgnoreCase(state)) {
				return issueState;
			}
		}
		return OPEN; // 기본값
	}
}
