package codesquad.team4.issuetracker.exception.unauthorized;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.USER_BY_EMAIL_NOT_EXIST;

public class UserByEmailNotFoundException extends UnauthorizedException {

    public UserByEmailNotFoundException() {
        super(USER_BY_EMAIL_NOT_EXIST);
    }
}
