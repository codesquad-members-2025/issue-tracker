package codesquad.team01.issuetracker.issue.service;

import codesquad.team01.issuetracker.issue.dto.*;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueQueryRepository issueQueryRepository;
    private final IssueAssembler issueAssembler;

    @Transactional(readOnly = true)
    public IssueDto.ListResponse findIssues(String state, Long writer, Long milestone,
                                        List<Long> labels, List<Long> assignees) {

        // 이슈 기본 정보 조회 - (담당자, 레이블 제외)
        List<IssueDto.BaseRow> issues = issueQueryRepository.findIssuesWithFilters(
                state, writer, milestone, labels, assignees);

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

        // 담당자와 레이블 정보 조회 - 이 작업을 issueQueryRepository에서 하는게 맞는건지? 각각의 repository에서 해야하는거 아닌지 고민
        List<IssueDto.AssigneeRow> allAssignees = issueQueryRepository.findAssigneesByIssueIds(issueIds);
        List<IssueDto.LabelRow> allLabels = issueQueryRepository.findLabelsByIssueIds(issueIds);
        log.debug("이슈 담당자 {}개, 레이블 {}개 조회", allAssignees.size(), allLabels.size());

        // 이슈 기본 정보와 담당자, 레이블 조합
        List<IssueDto.Details> issueDetails = issueAssembler.assembleIssueDetails(
                issues, allAssignees, allLabels);

        // 응답 dto로 변환
        List<IssueDto.SimpleResponse> issueResponses = issueDetails.stream()
                .map(IssueDto.Details::toSimpleResponse)
                .toList();

        log.debug("응답 데이터 생성 완료: 이슈 {}개 포함", issueResponses.size());
        return IssueDto.ListResponse.builder()
                .issues(issueResponses)
                .totalCount(issueResponses.size())
                .build();
    }
}
