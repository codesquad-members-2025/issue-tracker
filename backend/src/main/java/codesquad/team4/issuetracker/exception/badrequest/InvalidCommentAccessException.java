package codesquad.team4.issuetracker.exception.badrequest;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.INVALID_COMMENT_ACCESS;

public class InvalidCommentAccessException extends InvalidRequestException {
    public InvalidCommentAccessException() {
        super(INVALID_COMMENT_ACCESS);
    }
}
