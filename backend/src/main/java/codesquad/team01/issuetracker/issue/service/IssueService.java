package codesquad.team01.issuetracker.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.IssueDto;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelQueryRepository;
import codesquad.team01.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team01.issuetracker.milestone.repository.MilestoneQueryRepository;
import codesquad.team01.issuetracker.user.dto.UserDto;
import codesquad.team01.issuetracker.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueQueryRepository issueQueryRepository;
	private final UserQueryRepository userQueryRepository;
	private final LabelQueryRepository labelQueryRepository;
	private final MilestoneQueryRepository milestoneQueryRepository;

	private final IssueAssembler issueAssembler;

	public IssueDto.ListResponse findIssues(IssueState state, Integer writerId, Integer milestoneId,
		List<Integer> labelIds, List<Integer> assigneeIds, IssueDto.CursorData cursor) {

		final int PAGE_SIZE = issueQueryRepository.PAGE_SIZE;

		// 이슈 기본 정보 조회 - (담당자, 레이블 제외)
		List<IssueDto.BaseRow> issues = issueQueryRepository.findIssuesWithFilters(
			state, writerId, milestoneId, labelIds, assigneeIds, cursor);

		boolean hasNext = issues.size() > PAGE_SIZE; // 다음 페이지 존재 여부

		List<IssueDto.BaseRow> pagedIssues =
			hasNext ? issues.subList(0, PAGE_SIZE) : issues; // 다음 페이지가 있다면 PAGE_SIZE만큼만

		if (pagedIssues.isEmpty()) {
			log.debug("조건에 맞는 이슈가 없습니다.");
			return IssueDto.ListResponse.builder()
				.issues(List.of())
				.totalCount(0)
				.cursor(IssueDto.CursorResponse.builder()
					.next(null)
					.hasNext(false)
					.build())
				.build();
		}

		// 이슈 ID 목록
		List<Integer> issueIds = pagedIssues.stream()
			.map(IssueDto.BaseRow::issueId)
			.toList();
		log.debug("기본 이슈 {}개 조회, id 목록: {}", pagedIssues.size(), issueIds);

		// 드라이빙 테이블 기준으로 분리
		List<UserDto.IssueAssigneeRow> assignees = userQueryRepository.findAssigneesByIssueIds(issueIds);
		List<LabelDto.IssueLabelRow> labels = labelQueryRepository.findLabelsByIssueIds(issueIds);
		log.debug("이슈 담당자 {}개, 레이블 {}개 조회", assignees.size(), labels.size());

		// 이슈 기본 정보와 담당자, 레이블 조합
		List<IssueDto.Details> issueDetails = issueAssembler.assembleIssueDetails(
			pagedIssues, assignees, labels);

		// 응답 dto로 변환
		List<IssueDto.ListItemResponse> issueResponses = issueDetails.stream()
			.map(IssueDto.Details::toListItemResponse)
			.toList();

		// 커서 생성
		String next = null;
		if (hasNext && !pagedIssues.isEmpty()) { // 다음 페이지 있으면
			IssueDto.BaseRow lastIssue = pagedIssues.getLast();
			IssueDto.CursorData nextCursor = IssueDto.CursorData.builder()
				.id(lastIssue.issueId())
				.createdAt(lastIssue.issueCreatedAt())
				.build();
			log.debug("last issue CreatedAt: {}, issue id={}", lastIssue.issueCreatedAt(), lastIssue.issueId());
			next = nextCursor.encode();
		}

		log.debug("응답 데이터 생성 완료: 이슈 {}개 포함, 다음 페이지 존재: {}",
			issueResponses.size(), hasNext);
		return IssueDto.ListResponse.builder()
			.issues(issueResponses)
			.totalCount(issueResponses.size())
			.cursor(IssueDto.CursorResponse.builder()
				.next(next)
				.hasNext(hasNext)
				.build())
			.build();
	}

	public IssueDto.CountResponse countIssues(
		Integer writerId, Integer milestoneId, List<Integer> labelIds, List<Integer> assigneeIds) {

		log.debug("이슈 개수 조회: writerId={}, milestoneId={}, labelIds={}, assigneeIds={}",
			writerId, milestoneId, labelIds, assigneeIds);

		IssueDto.CountResponse response =
			issueQueryRepository.countIssuesWithFilters(writerId, milestoneId, labelIds, assigneeIds);

		log.debug("이슈 개수 조회 결과: 열린 이슈={}개, 닫힌 이슈={}개",
			response.open(), response.closed());

		return response;
	}

	public LabelDto.FilterLabelListResponse findLabelsForFilter() {

		List<LabelDto.FilterLabelListItemResponse> labelList = labelQueryRepository.findLabelList();

		return LabelDto.FilterLabelListResponse.builder()
			.totalCount(labelList.size())
			.labels(labelList)
			.build();
	}

	public MilestoneDto.FilterMilestoneListResponse findMilestonesForFilter() {

		List<MilestoneDto.FilterMilestoneListItemResponse> milestoneList = milestoneQueryRepository.findMilestoneList();

		return MilestoneDto.FilterMilestoneListResponse.builder()
			.totalCount(milestoneList.size())
			.milestones(milestoneList)
			.build();
	}

	public UserDto.FilterUserListResponse findUsersForFilter() {

		List<UserDto.WriterResponse> userList = userQueryRepository.findUserList();

		return UserDto.FilterUserListResponse.builder()
			.totalCount(userList.size())
			.users(userList)
			.build();
	}
}
