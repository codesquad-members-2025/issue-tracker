package elbin_bank.issue_tracker.issue.exception;

public class IssueDetailNotFoundException extends RuntimeException {
    public IssueDetailNotFoundException(long id) {
        super("Could not find issue detail with id " + id);
    }
}
