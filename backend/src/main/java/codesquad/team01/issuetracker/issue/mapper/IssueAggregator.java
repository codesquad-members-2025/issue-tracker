package codesquad.team01.issuetracker.issue.mapper;

import codesquad.team01.issuetracker.issue.dto.IssueJoinRow;
import codesquad.team01.issuetracker.issue.dto.IssueSimpleResponse;
import codesquad.team01.issuetracker.label.dto.LabelSimpleResponse;
import codesquad.team01.issuetracker.user.dto.UserSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IssueAggregator {

    private final IssueMapper issueMapper;

    public List<IssueSimpleResponse> aggregate(List<IssueJoinRow> rows) {
        Map<Long, IssueSimpleResponse> issueMap = new LinkedHashMap<>(); // 각 이슈의 기본 정보 저장
        Map<Long, Map<Long, UserSimpleResponse>> assigneesMap = new HashMap<>(); // 이슈별 담당자들을 저장
        Map<Long, Map<Long, LabelSimpleResponse>> labelsMap = new HashMap<>(); // 이슈별 레이블들을 저장

        for (IssueJoinRow row : rows) {
            Long issueId = row.issueId();

            issueMap.computeIfAbsent(issueId, id -> issueMapper.toIssueResponse(row));

            if (row.assigneeId() != null) {
                assigneesMap.computeIfAbsent(issueId, k -> new LinkedHashMap<>())
                        .putIfAbsent(row.assigneeId(), issueMapper.toAssignee(row));
            }

            if (row.labelId() != null) {
                labelsMap.computeIfAbsent(issueId, k -> new LinkedHashMap<>())
                        .putIfAbsent(row.labelId(), issueMapper.toLabel(row));
            }
        }

        return issueMap.entrySet().stream()
                .map(entry -> {
                    Long issueId = entry.getKey();
                    IssueSimpleResponse baseIssue = entry.getValue();

                    List<UserSimpleResponse> assignees = assigneesMap.getOrDefault(issueId, Map.of())
                            .values().stream().toList();
                    List<LabelSimpleResponse> labels = labelsMap.getOrDefault(issueId, Map.of())
                            .values().stream().toList();

                    return baseIssue.toBuilder()
                            .assignees(assignees)
                            .labels(labels)
                            .build();
                }).toList();
    }
}