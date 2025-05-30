package CodeSquad.IssueTracker.global.exception;

import CodeSquad.IssueTracker.global.exception.message.Error;
import CodeSquad.IssueTracker.global.exception.model.CustomException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return super.getHttpStatus();
    }

    public PasswordMismatchException() {
        super(Error.PASSWORD_MISMATCH_EXCEPTION);
    }
}
