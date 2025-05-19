package codesquad.team4.issuetracker.exception;

public class IssueNotFoundException extends RuntimeException {
    private final Long issueId;

    public IssueNotFoundException(Long issueId) {
        super("이슈를 찾을 수 없습니다. issueId = " + issueId);
        this.issueId = issueId;
    }

    public Long getIssueId() {
        return issueId;
    }
}
