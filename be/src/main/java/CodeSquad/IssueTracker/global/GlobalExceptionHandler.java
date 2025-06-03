package CodeSquad.IssueTracker.global;

import CodeSquad.IssueTracker.global.dto.BaseResponseDto;
import CodeSquad.IssueTracker.global.exception.model.CustomException;
import CodeSquad.IssueTracker.jwt.exception.JwtValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest()
                .body(BaseResponseDto.failure(errors));
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponseDto<String>> handleJwtValidationException(JwtValidationException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(BaseResponseDto.failure(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponseDto<String>> handleException(CustomException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest()
                .body(BaseResponseDto.failure(ex.getMessage()));
    }
}
