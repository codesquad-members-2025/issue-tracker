package elbin_bank.issue_tracker.common.advice;

import elbin_bank.issue_tracker.comment.exception.CommentNotFoundException;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.exception.MilestoneForIssueNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "elbin_bank.issue_tracker")
public class GlobalExceptionHandler {

    @ExceptionHandler({MilestoneForIssueNotFoundException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleNoContent() {
    }

    // 조회 결과가 없을 때 404만
    @ExceptionHandler({IssueDetailNotFoundException.class, CommentNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }

    // 그 외 서버 에러는 500만
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleServerError() {
    }

}
