package codesquad.team4.issuetracker.exception.notfound;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.USER_BY_EMAIL_NOT_EXIST;

public class UserByEmailNotFoundException extends DataNotFoundException {

    public UserByEmailNotFoundException() {
        super(USER_BY_EMAIL_NOT_EXIST);
    }
}
