package codesquad.team4.issuetracker.exception.unauthorized;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.USER_PASSWORD_NOT_EQUAL;

public class PasswordNotEqualException extends UnauthorizedException {
    public PasswordNotEqualException() {
        super(USER_PASSWORD_NOT_EQUAL);
    }
}
