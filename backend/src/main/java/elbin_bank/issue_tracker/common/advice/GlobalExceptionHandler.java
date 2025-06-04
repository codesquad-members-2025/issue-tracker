package elbin_bank.issue_tracker.common.advice;

import elbin_bank.issue_tracker.auth.exception.InvalidPasswordException;
import elbin_bank.issue_tracker.auth.exception.UserAlreadyExistsException;
import elbin_bank.issue_tracker.comment.exception.CommentNotFoundException;
import elbin_bank.issue_tracker.common.exception.EntityNotFoundException;
import elbin_bank.issue_tracker.common.exception.ForbiddenException;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.exception.MilestoneForIssueNotFoundException;
import elbin_bank.issue_tracker.label.exception.LabelNameDuplicateException;
import elbin_bank.issue_tracker.label.exception.LabelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IssueDetailNotFoundException.class, LabelNotFoundException.class, CommentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }

    @ExceptionHandler({EntityNotFoundException.class,
            InvalidPasswordException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadRequest() {
    }

    @ExceptionHandler({UserAlreadyExistsException.class, LabelNameDuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConflict() {
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbidden() {
    }

    // 그 외 서버 에러는 500만
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleServerError() {
    }

}
