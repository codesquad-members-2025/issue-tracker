package codesquad.team01.issuetracker.issue.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
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

	private final IssueService issueService;

	@GetMapping("/v1/issues")
	public ResponseEntity<ApiResponse<IssueDto.ListResponse>> getIssues(
		@Valid IssueDto.ListQueryRequest request) {

		log.info("이슈 목록 조회 요청: state={}, writerId={}, milestoneId={}, labelIds={}, assigneeIds={}, cursor={}",
			request.getState(), request.writerId(), request.milestoneId(),
			request.labelIds(), request.assigneeIds(), request.cursor());

		IssueDto.ListResponse response = issueService.findIssues(
			request.getIssueState(),
			request.writerId(),
			request.milestoneId(),
			request.labelIds(),
			request.assigneeIds(),
			request.decode()
		);

		log.info("조건에 부합하는 이슈 개수= {}, 다음 페이지 존재= {}",
			response.totalCount(), response.cursor().hasNext());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/v1/issues/count")
	public ResponseEntity<ApiResponse<IssueDto.CountResponse>> getIssuesCount(
		@Valid IssueDto.CountQueryRequest request) {

		log.info("이슈 목록 조회 요청: writerId={}, milestoneId={}, labelIds={}, assigneeIds={}",
			request.writerId(), request.milestoneId(), request.labelIds(), request.assigneeIds());

		IssueDto.CountResponse response = issueService.countIssues(
			request.writerId(),
			request.milestoneId(),
			request.labelIds(),
			request.assigneeIds()
		);

		log.info("조건에 부합하는 열린 이슈 개수= {}, 닫힌 이슈 개수= {}", response.open(), response.closed());
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
