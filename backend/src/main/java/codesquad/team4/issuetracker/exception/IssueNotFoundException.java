package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_ISSUE;

public class IssueNotFoundException extends RuntimeException {

    public IssueNotFoundException(Long issueId) {
        super(NOT_FOUND_ISSUE + issueId);
    }
}
