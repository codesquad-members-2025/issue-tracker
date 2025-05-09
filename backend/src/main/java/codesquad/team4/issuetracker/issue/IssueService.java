package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.LabelInfo;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.UserInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final JdbcTemplate jdbcTemplate;

    public IssueResponseDto.IssueListDto getIssues(boolean isOpen, int page, int size) {
        int offset = Math.max(0, (page - 1) * size);

        String sql = """
            SELECT i.issue_id AS issue_id,
                   i.title,
                   u.user_id AS author_id,
                   u.nickname AS author_nickname,
                   m.milestone_id AS milestone_id,
                   m.name AS milestone_title,
                   l.label_id AS label_id,
                   l.name AS label_name,
                   l.color AS label_color,
                   a.user_id AS assignee_id,
                   a.nickname AS assignee_nickname
            FROM issue i
            LEFT JOIN user u ON i.author_id = u.user_id
            LEFT JOIN milestone m ON i.milestone_id = m.milestone_id
            LEFT JOIN issue_label il ON i.issue_id = il.issue_id
            LEFT JOIN label l ON il.label_id = l.label_id
            LEFT JOIN issue_assignee ia ON i.issue_id = ia.issue_id
            LEFT JOIN user a ON ia.assignee_id = a.user_id
            WHERE i.is_open = ?
            LIMIT ? OFFSET ?
        """;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, isOpen, size, offset);

        Map<Long, IssueResponseDto.IssueInfo.IssueInfoBuilder> issueMap = new LinkedHashMap<>();
        Map<Long, List<IssueResponseDto.UserInfo>> assigneeMap = new HashMap<>();
        Map<Long, List<IssueResponseDto.LabelInfo>> labelMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long issueId = (Long) row.get("issue_id");

            issueMap.computeIfAbsent(issueId, id ->
                    IssueResponseDto.IssueInfo.builder()
                            .id(issueId)
                            .title((String) row.get("title"))
                            .author(IssueResponseDto.UserInfo.builder()
                                    .id((Long) row.get("author_id"))
                                    .name((String) row.get("author_nickname"))
                                    .build())
                            .assignees(new ArrayList<>())
                            .labels(new ArrayList<>())
                            .milestoneId((Long) row.get("milestone_id"))
                            .milestoneTitle((String) row.get("milestone_title"))
            );

            Long assigneeId = (Long) row.get("assignee_id");
            if (assigneeId != null) {
                IssueResponseDto.UserInfo assignee = UserInfo.builder()
                        .id(assigneeId)
                        .name((String) row.get("assignee_nickname"))
                        .build();

                assigneeMap.computeIfAbsent(issueId, k -> new ArrayList<>()).add(assignee);
            }


            Long labelId = (Long) row.get("label_id");
            if (labelId != null) {
                IssueResponseDto.LabelInfo label = LabelInfo.builder()
                        .id(labelId)
                        .name((String) row.get("label_name"))
                        .color((String) row.get("label_color"))
                        .build();

                labelMap.computeIfAbsent(issueId, k -> new ArrayList<>()).add(label);
            }
        }
        List<IssueResponseDto.IssueInfo> issues = new ArrayList<>();

        for (Map.Entry<Long, IssueResponseDto.IssueInfo.IssueInfoBuilder> entry : issueMap.entrySet()) {
            Long issueId = entry.getKey();
            IssueResponseDto.IssueInfo.IssueInfoBuilder builder = entry.getValue();

            builder.assignees(assigneeMap.getOrDefault(issueId, List.of()));
            builder.labels(labelMap.getOrDefault(issueId, List.of()));

            issues.add(builder.build());
        }

        int totalElements = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM issue WHERE is_open = ?",
                Integer.class,
                isOpen
        );

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return IssueResponseDto.IssueListDto.builder()
                .issues(issues)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }
}

