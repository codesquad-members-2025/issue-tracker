package codesquad.team4.issuetracker.exception.badrequest;


public class IssueStatusUpdateException extends InvalidRequestException {
    public IssueStatusUpdateException(String message) {
        super (message);
    }
}
