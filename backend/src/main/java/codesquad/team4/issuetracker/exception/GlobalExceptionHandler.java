package codesquad.team4.issuetracker.exception;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.FILE_UPLOAD_FAILED;
import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_ISSUE;
import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_MILESTONE;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
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

    @ExceptionHandler(MilestoneNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleMilestoneNotFoundException(MilestoneNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(NOT_FOUND_MILESTONE + ex.getMilestoneId()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ApiResponse<?>> handleFileUploadException(FileUploadException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(FILE_UPLOAD_FAILED));
    }
}