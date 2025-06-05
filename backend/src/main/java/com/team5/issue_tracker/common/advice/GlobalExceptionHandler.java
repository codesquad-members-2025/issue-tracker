package com.team5.issue_tracker.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.common.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {
    ApiResponse<Void> response =
        ApiResponse.failure(ex.getCustomMessage(), ex.getErrorCode().getStatus());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getDefaultMessage())
        .findFirst()
        .orElse("요청 형식이 잘못되었습니다.");

    ApiResponse<Void> response = ApiResponse.failure(errorMessage, 400);
    return ResponseEntity.badRequest().body(response);
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleUnknownException(Exception ex) {
    log.error("알 수 없는 오류가 발생했습니다.", ex);
    ApiResponse<Void> response = ApiResponse.failure("알 수 없는 서버 오류가 발생했습니다.", 500);
    return ResponseEntity.status(500).body(response);
  }
}
