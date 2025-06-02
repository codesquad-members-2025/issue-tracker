package codesquad.team01.issuetracker.common.exception;

public class IssueUpdateException extends RuntimeException {
	public IssueUpdateException(String message) {
		super(message);
	}

	public IssueUpdateException(String message, Throwable cause) {
		super(message, cause);
	}
}
