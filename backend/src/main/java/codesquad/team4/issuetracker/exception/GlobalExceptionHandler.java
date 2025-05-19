package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_ISSUE;

import codesquad.team4.issuetracker.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleIssueNotFoundException(IssueNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(NOT_FOUND_ISSUE + ex.getIssueId()));
    }

}