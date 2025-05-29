package codesquad.team4.issuetracker.exception.badrequest;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.USER_PASSWORD_NOT_EQUAL;

public class PasswordNotEqualException extends InvalidRequestException {
    public PasswordNotEqualException() {
        super(USER_PASSWORD_NOT_EQUAL);
    }
}
