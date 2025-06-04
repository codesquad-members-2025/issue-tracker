package codesquad.team01.issuetracker.common.exception;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.issue.exception.IssueCreationException;
import codesquad.team01.issuetracker.issue.exception.IssueNotFoundException;
import codesquad.team01.issuetracker.milestone.exception.MilestoneNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ApiResponse<?>> handleInvalidParameterException(InvalidParameterException e) {
		log.error("InvalidParameterException: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class) // @Valid에서 발생하는 에러
	public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
		String errorMessage = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(", "));

		log.error("Bean Validation error(@Valid): {}", errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(errorMessage));
	}

	// 레이블 생성 시 이미 존재하는 레이블과 이름 중복
	@ExceptionHandler(DuplicateLabelNameException.class)
	public ResponseEntity<ApiResponse<?>> handleDuplicateLabel(DuplicateLabelNameException e) {
		String labelName = e.getLabelName();
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error("레이블 이름 '" + labelName + "'은(는) 이미 존재합니다."));
	}

	// 레이블을 찾을 수 없을 때
	@ExceptionHandler(LabelNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleLabelNotFound(LabelNotFoundException e) {
		int id = e.getId();
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error("레이블 '" + id + "' 을(를) 찾을 수 없습니다."));
	}

	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ApiResponse<?>> handleInvalidDate(InvalidDateException e) {
		log.error("InvalidDateException: {}", e.getMessage());
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(e.getMessage()));
	}

	@ExceptionHandler(MilestoneNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleMilestoneNotFound(MilestoneNotFoundException e) {
		int id = e.getId();
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error("마일스톤 '" + id + "' 을(를) 찾을 수 없습니다."));
	}

	@ExceptionHandler(DuplicateMilestoneTitleException.class)
	public ResponseEntity<ApiResponse<?>> handleDuplicateMilestone(DuplicateMilestoneTitleException e) {
		String title = e.getMilestoneTitle();
		return ResponseEntity
			.badRequest().
			body(ApiResponse.error("마일스톤 이름 '" + title + "'은(는) 이미 존재합니다."));
	}

	// 아이디가 존재하지 않는 경우
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleUserNotFound(UserNotFoundException e) {
		log.error("UserNotFoundException : {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("존재하지 않는 ID 입니다"));
	}

	// 패스워드가 틀린 경우
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiResponse<?>> handleInvalidPassword(InvalidPasswordException e) {
		log.error("InvalidPasswordException : {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("비밀번호가 일치하지 않습니다"));
	}

	// 이슈 생성 실패 에러
	@ExceptionHandler(IssueCreationException.class)
	public ResponseEntity<ApiResponse<?>> handleIssueCreationException(IssueCreationException e) {
		log.error("IssueCreationException: {}", e.getMessage());
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.error(e.getMessage()));
	}

	// 마일스톤 없음 에러
	@ExceptionHandler(MilestoneNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleMilestoneNotFoundException(MilestoneNotFoundException e) {
		log.error("MilestoneNotFoundException: {}", e.getMessage());
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(ApiResponse.error(e.getMessage()));
	}

	// 이슈 없음 에러
	@ExceptionHandler(IssueNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleIssueNotFoundException(IssueNotFoundException e) {
		log.error("IssueNotFoundException: {}", e.getMessage());
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(ApiResponse.error(e.getMessage()));
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ApiResponse<?>> handleDataAccessException(DataAccessException e) {
		log.error("Database error occurred", e);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.error("데이터베이스 오류가 발생했습니다."));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleExceptions(Exception e) {
		log.error("Unhandled exception", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("서버 내부 오류가 발생했습니다."));
	}

}
