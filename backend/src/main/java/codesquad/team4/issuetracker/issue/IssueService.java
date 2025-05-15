package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.Issue.IssueBuilder;
import codesquad.team4.issuetracker.entity.IssueAssignee;
import codesquad.team4.issuetracker.entity.IssueLabel;
import codesquad.team4.issuetracker.exception.IssueStatusUpdateException;
import codesquad.team4.issuetracker.exception.ExceptionMessage;
import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.IssueLabelRepository;
import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team4.issuetracker.user.IssueAssigneeRepository;
import codesquad.team4.issuetracker.user.dto.UserDto.UserInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import codesquad.team4.issuetracker.label.dto.LabelDto;
import codesquad.team4.issuetracker.user.dto.UserDto;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueService {
    private static final String CREATE_ISSUE = "이슈가 생성되었습니다";
    private static final String UPDATE_ISSUESTATUS = "이슈 상태가 변경되었습니다.";

    private final IssueDao issueDao;
    private final IssueRepository issueRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;

    public IssueResponseDto.IssueListDto getIssues(boolean isOpen, int page, int size) {
        List<Map<String, Object>> rows = issueDao.findIssuesByOpenStatus(isOpen, page, size);

        Map<Long, IssueResponseDto.IssueInfo.IssueInfoBuilder> issueMap = new LinkedHashMap<>();
        Map<Long, Set<UserInfo>> assigneeMap = new HashMap<>();
        Map<Long, Set<LabelDto.LabelInfo>> labelMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long issueId = (Long) row.get("issue_id");

            issueMap.computeIfAbsent(issueId, id ->
                    IssueResponseDto.IssueInfo.builder()
                            .id(issueId)
                            .title((String) row.get("title"))
                            .author(UserDto.UserInfo.builder()
                                    .id((Long) row.get("author_id"))
                                    .nickname((String) row.get("author_nickname"))
                                    .profileImage((String) row.get("author_profile"))
                                    .build())
                            .assignees(new ArrayList<>())
                            .labels(new ArrayList<>())
                            .milestone(MilestoneDto.MilestoneInfo.builder()
                                    .id((Long) row.get("milestone_id"))
                                    .title((String) row.get("milestone_title"))
                                    .build())
            );

            Long assigneeId = (Long) row.get("assignee_id");
            if (assigneeId != null) {
                UserDto.UserInfo assignee = UserDto.UserInfo.builder()
                        .id(assigneeId)
                        .nickname((String) row.get("assignee_nickname"))
                        .profileImage((String) row.get("assignee_profile"))
                        .build();

                assigneeMap.computeIfAbsent(issueId, k -> new HashSet<>()).add(assignee);
            }


            Long labelId = (Long) row.get("label_id");
            if (labelId != null) {
                LabelDto.LabelInfo label = LabelDto.LabelInfo.builder()
                        .id(labelId)
                        .name((String) row.get("label_name"))
                        .color((String) row.get("label_color"))
                        .build();

                labelMap.computeIfAbsent(issueId, k -> new HashSet<>()).add(label);
            }
        }
        List<IssueResponseDto.IssueInfo> issues = new ArrayList<>();

        for (Map.Entry<Long, IssueResponseDto.IssueInfo.IssueInfoBuilder> entry : issueMap.entrySet()) {
            Long issueId = entry.getKey();
            IssueResponseDto.IssueInfo.IssueInfoBuilder builder = entry.getValue();

            builder.assignees(List.copyOf(assigneeMap.getOrDefault(issueId, Set.of())));
            builder.labels(List.copyOf(labelMap.getOrDefault(issueId, Set.of())));

            issues.add(builder.build());
        }

        int totalElements = issueDao.countIssuesByOpenStatus(isOpen);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return IssueResponseDto.IssueListDto.builder()
                .issues(issues)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    @Transactional
    public IssueResponseDto.CreateIssueDto createIssue(IssueRequestDto.CreateIssueDto request, String uploadUrl) {
        IssueBuilder issueBuilder = Issue.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(uploadUrl)
                .isOpen(true)
                .authorId(request.getAuthorId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now());

        Optional.ofNullable(request.getMilestoneId())
                .ifPresent(issueBuilder::milestoneId);

        Issue issue = issueBuilder.build();
        Issue savedIssue = issueRepository.save(issue);

        Long issueId = savedIssue.getId();

        for (Long labelId : Optional.ofNullable(request.getLabelId()).orElse(List.of())) {
            IssueLabel issueLabel = IssueLabel.builder()
                    .issueId(issueId)
                    .labelId(labelId)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            issueLabelRepository.save(issueLabel);
        }

        for (Long assigneeId : Optional.ofNullable(request.getAssigneeId()).orElse(List.of())) {
            IssueAssignee issueAssignee = IssueAssignee.builder()
                    .issueId(issueId)
                    .assigneeId(assigneeId)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            issueAssigneeRepository.save(issueAssignee);
        }

        return IssueResponseDto.CreateIssueDto.builder()
                .id(issueId)
                .message(CREATE_ISSUE)
                .build();
    }

    @Transactional
    public IssueResponseDto.BulkUpdateIssueStatusDto bulkUpdateIssueStatus(IssueRequestDto.BulkUpdateIssueStatusDto request) {
        List<Long> issueIds = request.getIssuesId();
        boolean isOpen = request.isOpen();

        if (issueIds == null || issueIds.isEmpty()) {
            throw new IssueStatusUpdateException(ExceptionMessage.NO_ISSUE_IDS);
        }

        String placeholders = issueIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        Integer count = issueDao.countExistingIssuesByIds(issueIds, placeholders);

        if (count == null || count != issueIds.size()) {
            throw new IssueStatusUpdateException(ExceptionMessage.ISSUE_IDS_NOT_FOUND); //todo 나중에 여기에 존재하지 않는 id 가 정확히 몇번인지 추가해주기
        }


        List<Object> params = new ArrayList<>();
        params.add(isOpen);
        params.addAll(issueIds);
        issueDao.updateIssueStatus(placeholders, params);

        return IssueResponseDto.BulkUpdateIssueStatusDto.builder()
                .issuesId(issueIds)
                .message(UPDATE_ISSUESTATUS)
                .build();

    }

    public IssueCountDto getIssueCounts() {
        //// 쿼리 결과가 null일 경우 NPE 방지를 위해 int 대신 Integer 사용 -> queryForObject(...)가 null반환 할 수 있음
        Integer openCount = issueDao.countIssuesByOpenStatus(true);
        Integer closedCount = issueDao.countIssuesByOpenStatus(false);

        return IssueCountDto.builder()
                .openCount(openCount)
                .closedCount(closedCount)
                .build();
    }
}

