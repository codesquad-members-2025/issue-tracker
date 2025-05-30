package CodeSquad.IssueTracker.global.exception;

import CodeSquad.IssueTracker.global.exception.message.Error;
import CodeSquad.IssueTracker.global.exception.model.CustomException;
import org.springframework.http.HttpStatus;

public class NoAuthorityException extends CustomException {

    public NoAuthorityException() {
        super(Error.NO_AUTHORITY_EXCEPTION);
    }
}
