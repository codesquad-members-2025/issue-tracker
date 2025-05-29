package CodeSquad.IssueTracker.global.exception;

import CodeSquad.IssueTracker.global.exception.model.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends CustomException {
    private final HttpStatus httpStatus;

    public ApplicationException() {

    }

}
