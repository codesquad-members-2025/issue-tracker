package codesquad.team01.issuetracker.issue.service;

import codesquad.team01.issuetracker.issue.dto.IssueJoinRow;
import codesquad.team01.issuetracker.issue.dto.IssueListResponse;
import codesquad.team01.issuetracker.issue.dto.IssueSimpleResponse;
import codesquad.team01.issuetracker.issue.mapper.IssueAggregator;
import codesquad.team01.issuetracker.issue.repository.IssueQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

    private final IssueQueryRepository issueQueryRepository;
    private final IssueAggregator issueAggregator;

    public IssueListResponse findIssues(String state, Long author, Long milestone,
                                       String labels, String assignees) {
        // 파라미터 파싱
        List<Long> labelIds = parseIds(labels);
        List<Long> assigneeIds = parseIds(assignees);

        // join row 조회
        List<IssueJoinRow> rows = issueQueryRepository.findIssuesWithFilters(
                state, author, milestone, labelIds, assigneeIds);

        // aggregator로 하나의 이슈에 대한 중복된 row 합치기 (담장자, 레이블)
        List<IssueSimpleResponse> issues = issueAggregator.aggregate(rows);

        return IssueListResponse.builder()
                .issues(issues)
                .totalCount(issues.size())
                .build();
    }

    private List<Long> parseIds(String idsString) {
        if (idsString == null || idsString.isEmpty()) {
            return null;
        }
        return Arrays.stream(idsString.split(","))
                .map(Long::parseLong)
                .toList();
    }
}
