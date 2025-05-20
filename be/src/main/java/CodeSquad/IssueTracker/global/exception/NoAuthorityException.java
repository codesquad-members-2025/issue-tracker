package CodeSquad.IssueTracker.global.exception;

import CodeSquad.IssueTracker.global.exception.model.CustomException;
import org.springframework.http.HttpStatus;

public class NoAuthorityException extends CustomException {

    public NoAuthorityException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
