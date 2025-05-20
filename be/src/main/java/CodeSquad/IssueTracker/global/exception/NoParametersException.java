package CodeSquad.IssueTracker.global.exception;

import CodeSquad.IssueTracker.global.exception.model.CustomException;
import org.springframework.http.HttpStatus;

public class NoParametersException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return super.getHttpStatus();
    }

    public NoParametersException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
