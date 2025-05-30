package CodeSquad.IssueTracker.global.exception.model;

import CodeSquad.IssueTracker.global.exception.message.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;

    protected CustomException(Error error) {
        super(error.getMessage());
        this.httpStatus = error.getStatus();
    }

    protected CustomException(Error error, String formattedMessage) {
        super(formattedMessage);
        this.httpStatus = error.getStatus();
    }

}
