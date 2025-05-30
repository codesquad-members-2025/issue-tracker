package codesquad.team4.issuetracker.exception.badrequest;

import codesquad.team4.issuetracker.exception.ExceptionMessage;

public class InvalidOAuthStateException extends InvalidRequestException {
    public InvalidOAuthStateException() {
        super(ExceptionMessage.INVALID_OAUTH_STATE);
    }
}
