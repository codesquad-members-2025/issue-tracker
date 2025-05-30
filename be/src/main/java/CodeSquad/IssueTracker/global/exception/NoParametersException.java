package CodeSquad.IssueTracker.global.exception;

import CodeSquad.IssueTracker.global.exception.message.Error;
import CodeSquad.IssueTracker.global.exception.model.CustomException;
import org.springframework.http.HttpStatus;

public class NoParametersException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return super.getHttpStatus();
    }

    public NoParametersException(String message) {
        super(Error.NO_PARAMETERS,Error.NO_PARAMETERS.formatMessageOf(message));
    }
}
