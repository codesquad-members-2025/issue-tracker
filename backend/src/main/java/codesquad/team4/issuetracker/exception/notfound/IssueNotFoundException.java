package codesquad.team4.issuetracker.exception.notfound;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_ISSUE;

public class IssueNotFoundException extends DataNotFoundException {

    public IssueNotFoundException(Long issueId) {
        super(NOT_FOUND_ISSUE + issueId);
    }
}
