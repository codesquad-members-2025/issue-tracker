package CodeSquad.IssueTracker.jwt.exception;

import CodeSquad.IssueTracker.global.exception.message.Error;
import CodeSquad.IssueTracker.global.exception.model.CustomException;

public class JwtValidationException extends CustomException {

    public JwtValidationException(String message) {
        super(Error.CUSTOM_MESSAGE_EXCEPTION, message);
    }
}
