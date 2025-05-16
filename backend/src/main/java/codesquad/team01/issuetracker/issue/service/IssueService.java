package codesquad.team01.issuetracker.issue.service;

import codesquad.team01.issuetracker.issue.dto.IssueJoinRow;
import codesquad.team01.issuetracker.issue.dto.IssueListResponse;
import codesquad.team01.issuetracker.issue.dto.IssueSimpleResponse;
import codesquad.team01.issuetracker.issue.mapper.IssueAggregator;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueQueryRepository issueQueryRepository;
    private final IssueAggregator issueAggregator;

    @Transactional(readOnly = true)
    public IssueListResponse findIssues(String state, Long author, Long milestone,
                                       List<Long> labels, List<Long> assignees) {
        // join row 조회
        List<IssueJoinRow> rows = issueQueryRepository.findIssuesWithFilters(
                state, author, milestone, labels, assignees);

        // aggregator로 하나의 이슈에 대한 중복된 row 합치기 (담장자, 레이블)
        List<IssueSimpleResponse> issues = issueAggregator.aggregate(rows);

        return IssueListResponse.builder()
                .issues(issues)
                .totalCount(issues.size())
                .build();
    }
}
