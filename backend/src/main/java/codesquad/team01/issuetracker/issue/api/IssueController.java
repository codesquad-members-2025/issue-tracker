package codesquad.team01.issuetracker.issue.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.team01.issuetracker.common.annotation.CursorParam;
import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.common.dto.CursorDto;
import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class IssueController {

	private static final Integer FIRST_USER_ID = 1; // 임시 사용자 id

	private final IssueService issueService;

	@GetMapping("/v1/issues")
	public ResponseEntity<ApiResponse<IssueDto.ListResponse>> getIssues(
		@Valid IssueDto.ListQueryRequest request,
		@CursorParam CursorDto.CursorData cursor) {

		log.info(request.toString());

		IssueDto.ListResponse response = issueService.findIssues(request, cursor);

		log.info("조건에 부합하는 이슈 개수= {}, 다음 페이지 존재= {}",
			response.totalCount(), response.cursor().hasNext());
		return ResponseEntity.ok(ApiResponse.success(response));
		
	}

	@GetMapping("/v1/issues/count")
	public ResponseEntity<ApiResponse<IssueDto.CountResponse>> getIssuesCount(
		@Valid IssueDto.CountQueryRequest request) {

		log.info(request.toString());

		IssueDto.CountResponse response = issueService.countIssues(request);

		log.info(response.toString());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PatchMapping("/v1/issues/batch")
	public ResponseEntity<ApiResponse<IssueDto.BatchUpdateResponse>> batchUpdateIssueState(
		@Valid @RequestBody IssueDto.BatchUpdateRequest request) {

		log.info(request.toString());

		IssueDto.BatchUpdateResponse response =
			issueService.batchUpdateIssueState(request);

		log.info(response.toString());

		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PostMapping("/v1/issues")
	public ResponseEntity<ApiResponse<IssueDto.CreateResponse>> createIssue(
		@Valid @RequestBody IssueDto.CreateRequest request/*, Integer userId*/) { // filter 구현 시 적용

		log.info(request.toString());

		IssueDto.CreateResponse response = issueService.createIssue(request, FIRST_USER_ID);
		URI location = URI.create("/api/v1/issues" + response.id());

		log.info("새로운 이슈 생성 id: {}", response.id());

		return ResponseEntity.created(location)
			.body(ApiResponse.success(response));
	}

	@PatchMapping("/v1/issues/{id}")
	public ResponseEntity<ApiResponse<IssueDto.CreateResponse>> updateIssue(
		@PathVariable Integer id,
		@RequestBody @Valid IssueDto.UpdateRequest request) {

		log.info("이슈 수정 요청: issueId={}, request={}", id, request);

		IssueDto.CreateResponse response = issueService.updateIssue(id, request, FIRST_USER_ID);

		log.info("이슈 수정 완료: issueId={}", id);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}



