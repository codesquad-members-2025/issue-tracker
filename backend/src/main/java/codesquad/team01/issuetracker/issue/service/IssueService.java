package codesquad.team01.issuetracker.issue.service;

import codesquad.team01.issuetracker.issue.domain.IssueState;
import codesquad.team01.issuetracker.issue.dto.*;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelQueryRepository;
import codesquad.team01.issuetracker.user.dto.UserDto;
import codesquad.team01.issuetracker.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueQueryRepository issueQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final LabelQueryRepository labelQueryRepository;
    private final IssueAssembler issueAssembler;

    public IssueDto.ListResponse findIssues(IssueState state, Long writerId, Long milestoneId,
                                            List<Long> labelIds, List<Long> assigneeIds) {

        // 이슈 기본 정보 조회 - (담당자, 레이블 제외)
        List<IssueDto.BaseRow> issues = issueQueryRepository.findIssuesWithFilters(
                state, writerId, milestoneId, labelIds, assigneeIds);

        if (issues.isEmpty()) {
            log.debug("조건에 맞는 이슈가 없습니다.");
            return IssueDto.ListResponse.builder()
                    .issues(List.of())
                    .totalCount(0)
                    .build();
        }

        // 이슈 ID 목록
        List<Long> issueIds = issues.stream()
                .map(IssueDto.BaseRow::issueId)
                .toList();
        log.debug("기본 이슈 {}개 조회, id 목록: {}", issues.size(), issueIds);

        // 드라이빙 테이블 기준으로 분리
        List<UserDto.IssueAssigneeRow> assignees = userQueryRepository.findAssigneesByIssueIds(issueIds);
        List<LabelDto.IssueLabelRow> labels = labelQueryRepository.findLabelsByIssueIds(issueIds);
        log.debug("이슈 담당자 {}개, 레이블 {}개 조회", assignees.size(), labels.size());

        // 이슈 기본 정보와 담당자, 레이블 조합
        List<IssueDto.Details> issueDetails = issueAssembler.assembleIssueDetails(
                issues, assignees, labels);

        // 응답 dto로 변환
        List<IssueDto.ListItemResponse> issueResponses = issueDetails.stream()
                .map(IssueDto.Details::toListItemResponse)
                .toList();

        log.debug("응답 데이터 생성 완료: 이슈 {}개 포함", issueResponses.size());
        return IssueDto.ListResponse.builder()
                .issues(issueResponses)
                .totalCount(issueResponses.size())
                .build();
    }
}
