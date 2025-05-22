package codesquad.team01.issuetracker.issue.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.team01.issuetracker.common.dto.ApiResponse;
import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.service.IssueService;
import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team01.issuetracker.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/issues")
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

	@GetMapping("/issues/count")
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

	// 이슈의 메타데이터인 것 같아 IssueController에 두었는데, 이것들도 각각의 controller에서 구현해야하는지
	// 이것 또한 드라이빙 테이블 기준??
	@GetMapping("/filters/labels")
	public ResponseEntity<ApiResponse<LabelDto.LabelFilterListResponse>> getLabels() {
		LabelDto.LabelFilterListResponse response = issueService.findLabelsForFilter();
		log.info("레이블 목록 개수= {}", response.totalCount());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/filters/milestones")
	public ResponseEntity<ApiResponse<MilestoneDto.MilestoneFilterListResponse>> getMilestones() {
		MilestoneDto.MilestoneFilterListResponse response = issueService.findMilestonesForFilter();
		log.info("마일스톤 목록 개수= {}", response.totalCount());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/filters/users")
	public ResponseEntity<ApiResponse<UserDto.UserFilterListResponse>> getUsers() {
		UserDto.UserFilterListResponse response = issueService.findUsersForFilter();
		log.info("사용자 목록 개수= {}", response.totalCount());
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
