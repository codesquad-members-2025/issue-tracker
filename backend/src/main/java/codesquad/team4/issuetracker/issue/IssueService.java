package codesquad.team4.issuetracker.issue;

import static codesquad.team4.issuetracker.aws.S3FileService.EMPTY_STRING;

import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.count.dto.IssueCountDto;
import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.IssueAssignee;
import codesquad.team4.issuetracker.entity.IssueLabel;
import codesquad.team4.issuetracker.entity.User;
import codesquad.team4.issuetracker.exception.notfound.AssigneeNotFoundException;
import codesquad.team4.issuetracker.exception.notfound.IssueNotFoundException;
import codesquad.team4.issuetracker.exception.notfound.LabelNotFoundException;
import codesquad.team4.issuetracker.exception.notfound.MilestoneNotFoundException;
import codesquad.team4.issuetracker.exception.unauthorized.NotAuthorException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto.IssueUpdateDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.ApiMessageDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.IssueInfo;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.searchIssueDetailDto;
import codesquad.team4.issuetracker.label.IssueLabelRepository;
import codesquad.team4.issuetracker.label.LabelDao;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto.LabelInfo;
import codesquad.team4.issuetracker.milestone.MilestoneRepository;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.user.AssigneeDao;
import codesquad.team4.issuetracker.user.IssueAssigneeRepository;
import codesquad.team4.issuetracker.user.UserDao;
import codesquad.team4.issuetracker.user.dto.UserDto;
import codesquad.team4.issuetracker.user.dto.UserDto.UserInfo;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueService {
    private static final String CREATE_ISSUE = "이슈가 생성되었습니다";
    private static final String UPDATE_ISSUESTATUS = "이슈 상태가 변경되었습니다.";
    private static final String UPDATE_ISSUE = "이슈가 수정되었습니다";
    private static final String ISSUE_PARTIALLY_UPDATED = "일부 이슈 ID는 존재하지 않아 제외되었습니다: %s";
    private static final String UPDATE_ISSUE_LABEL = "이슈의 레이블이 수정되었습니다";
    private static final String UPDATE_ISSUE_ASSIGNEE = "이슈의 담당자가 수정되었습니다";

    private final IssueDao issueDao;
    private final LabelDao labelDao;
    private final UserDao userDao;
    private final AssigneeDao assigneeDao;
    private final IssueRepository issueRepository;
    private final IssueLabelRepository issueLabelRepository;
    private final IssueAssigneeRepository issueAssigneeRepository;
    private final MilestoneRepository milestoneRepository;
    private final ApplicationEventPublisher eventPublisher;

    public IssueResponseDto.IssueListDto getIssues(IssueRequestDto.IssueFilterParamDto params, int page, int size) {
        List<Map<String, Object>> rows = issueDao.findIssuesByOpenStatus(params, page, size);

        Map<Long, IssueResponseDto.IssueInfo> issueMap = new LinkedHashMap<>();
        Map<Long, Set<UserInfo>> assigneeMap = new HashMap<>();
        Map<Long, Set<LabelResponseDto.LabelInfo>> labelMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long issueId = (Long) row.get("issue_id");

            addAssigneeToMap(row, assigneeMap, issueId);
            addLabelToMap(row, labelMap, issueId);
            addIssueToMap(row, issueMap, issueId, assigneeMap, labelMap);
        }

        Map<String, Integer> countMap = issueDao.countFilteredIssues(params);
        int openCount = countMap.get("open");
        int closeCount = countMap.get("close");

        int totalElements = openCount + closeCount;
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return IssueResponseDto.IssueListDto.builder()
            .issues(new ArrayList<>(issueMap.values()))
            .openCount(openCount)
            .closeCount(closeCount)
            .page(page)
            .size(size)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .build();
    }

    private Optional<Integer> getIssueCountByStatus(List<Map<String, Object>> rows, String columnName) {
        Map<String, Object> row = rows.stream()
            .findFirst()
            .orElse(Map.of());

        return Optional.ofNullable((Long) row.get(columnName)).map(Long::intValue);
    }

    private void addIssueToMap(Map<String, Object> row, Map<Long, IssueInfo> issueMap, Long issueId,
                                  Map<Long, Set<UserInfo>> assigneeMap, Map<Long, Set<LabelInfo>> labelMap) {
        issueMap.computeIfAbsent(issueId, id ->
                IssueInfo.builder()
                        .id(issueId)
                        .title((String) row.get("title"))
                        .author(UserInfo.builder()
                                .id((Long) row.get("author_id"))
                                .nickname((String) row.get("author_nickname"))
                                .profileImage((String) row.get("author_profile"))
                                .build())
                        .assignees(assigneeMap.getOrDefault(issueId, Set.of()))
                        .labels(labelMap.getOrDefault(issueId, Set.of()))
                        .milestone(MilestoneResponseDto.MilestoneInfo.builder()
                                .id((Long) row.get("milestone_id"))
                                .title((String) row.get("milestone_title"))
                                .build())
                        .build()
        );
    }

    private void addLabelToMap(Map<String, Object> row, Map<Long, Set<LabelInfo>> labelMap, Long issueId) {
        Long labelId = (Long) row.get("label_id");
        if (labelId != null) {
            LabelInfo label = LabelInfo.builder()
                    .id(labelId)
                    .name((String) row.get("label_name"))
                    .color((String) row.get("label_color"))
                    .build();

            labelMap.computeIfAbsent(issueId, k -> new HashSet<>()).add(label);
        }
    }

    private void addAssigneeToMap(Map<String, Object> row, Map<Long, Set<UserInfo>> assigneeMap, Long issueId) {
        Long assigneeId = (Long) row.get("assignee_id");
        if (assigneeId != null) {
            UserInfo assignee = UserInfo.builder()
                    .id(assigneeId)
                    .nickname((String) row.get("assignee_nickname"))
                    .profileImage((String) row.get("assignee_profile"))
                    .build();

            assigneeMap.computeIfAbsent(issueId, k -> new HashSet<>()).add(assignee);
        }
    }

    @Transactional
    public ApiMessageDto createIssue(IssueRequestDto.CreateIssueDto request, String uploadUrl) {
        Issue issue = Issue.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .FileUrl(uploadUrl)
                .isOpen(true)
                .authorId(request.getAuthorId())
                .milestoneId(request.getMilestoneId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Issue savedIssue = issueRepository.save(issue);

        Long issueId = savedIssue.getId();

        if(request.getLabelIds() != null) {
            addNewLabels(issueId, request.getLabelIds());
        }

        if(request.getAssigneeIds() != null) {
            addNewAssignees(issueId, request.getAssigneeIds());
        }

        eventPublisher.publishEvent(new IssueEvent.Created());
        return createMessageResult(issueId, CREATE_ISSUE);
    }

    @Transactional
    public IssueResponseDto.BulkUpdateIssueStatusDto bulkUpdateIssueStatus(IssueRequestDto.BulkUpdateIssueStatusDto request) {
        List<Long> issueIds  = request.getIssuesId();
        boolean   targetOpen = request.isOpen();

        List<Issue> existing = issueRepository.findAllByIdIn(issueIds);
        Map<Long, Boolean> oldStatusMap = existing.stream()
                .collect(Collectors.toMap(Issue::getId, Issue::isOpen));

        List<Long> existingIds = new ArrayList<>(oldStatusMap.keySet());
        if (!existingIds.isEmpty()) {
            issueDao.updateIssueStatusByIds(targetOpen, existingIds);
        }

        // 변경된 이슈들에 대해 이벤트 발행
        for (Long id : existingIds) {
            boolean oldOpen = oldStatusMap.get(id);
            if (oldOpen != targetOpen) {
                eventPublisher.publishEvent(
                        new IssueEvent.StatusChanged(oldOpen, targetOpen)
                );
            }
        }

        List<Long> missingIds = issueIds.stream()
                .filter(id -> !oldStatusMap.containsKey(id))
                .toList();
        String message = missingIds.isEmpty()
                ? UPDATE_ISSUESTATUS
                : String.format(ISSUE_PARTIALLY_UPDATED, missingIds);

        return IssueResponseDto.BulkUpdateIssueStatusDto.builder()
                .issuesId(existingIds)
                .message(message)
                .build();
    }

    public IssueCountDto getIssueCounts() {
        Integer openCount = issueDao.countIssuesByOpenStatus(true);
        Integer closedCount = issueDao.countIssuesByOpenStatus(false);

        return IssueCountDto.builder()
                .openCount(openCount != null ? openCount : 0)
                .closedCount(closedCount != null ? closedCount : 0)
                .build();
    }

    public searchIssueDetailDto getIssueDetailById(Long issueId) {
        List<Map<String, Object>> issueById = issueDao.findIssueDetailById(issueId);
        if (issueById.isEmpty()) {
            throw new IssueNotFoundException(issueId);
        }

        String issueContent = (String) issueById.get(0).get("issue_content");
        String issueImage = (String) issueById.get(0).get("issue_file_url");

        List<CommentResponseDto.CommentInfo> comments = issueById.stream()
                .filter(row -> row.get("comment_id") != null) // 댓글이 없는 경우 필터링
                .map(row -> CommentResponseDto.CommentInfo.builder()
                        .commentId((Long) row.get("comment_id"))
                        .content((String) row.get("comment_content"))
                        .fileUrl((String) row.get("comment_file_url"))
                        .createdAt(((Timestamp) row.get("comment_created_at")).toLocalDateTime())
                        .author(UserDto.UserInfo.builder()
                                .id((Long) row.get("author_id"))
                                .nickname((String) row.get("author_nickname"))
                                .profileImage((String) row.get("author_profile"))
                                .build())
                        .build())
                .toList();

        return IssueResponseDto.searchIssueDetailDto.builder()
                .content(issueContent)
                .contentFileUrl(issueImage)
                .comments(comments)
                .commentSize(comments.size())
                .build();
    }

    @Transactional
    public ApiMessageDto updateIssue(Long id, IssueRequestDto.IssueUpdateDto request, String uploadUrl, User user) {
        Issue oldIssue = issueRepository.findById(id)
                .orElseThrow(() -> new IssueNotFoundException(id));

        //작성자 검증
        validateAuthor(oldIssue.getAuthorId(), user);

        if (request.getMilestoneId() != null) {
            milestoneRepository.findById(request.getMilestoneId())
                    .orElseThrow(() -> new MilestoneNotFoundException(request.getMilestoneId()));
        }

        boolean oldOpen = oldIssue.isOpen();

        //기존 이미지를 삭제하는 것인지 확인
        String newFileUrl = determineNewFileUrl(request, uploadUrl, oldIssue);

        Issue updated = createupdatedIssue(request, oldIssue, newFileUrl);

        issueRepository.save(updated);

        boolean newOpen = updated.isOpen();

        //상태가 바뀌었을 때만 이벤트 발생
        if (oldOpen != newOpen) {
            eventPublisher.publishEvent(
                    new IssueEvent.StatusChanged(oldOpen, newOpen)
            );
        }

        return createMessageResult(updated.getId(), UPDATE_ISSUE);
    }

    private  Issue createupdatedIssue(IssueUpdateDto request, Issue oldIssue, String newFileUrl) {
        return oldIssue.toBuilder()
                .title(request.getTitle() != null ? request.getTitle() : oldIssue.getTitle())
                .content(request.getContent() != null ? request.getContent() : oldIssue.getContent())
                .FileUrl(newFileUrl)
                .milestoneId(request.getMilestoneId() != null ? request.getMilestoneId() : oldIssue.getMilestoneId())
                .isOpen(request.getIsOpen() != null ? request.getIsOpen() : oldIssue.isOpen())
                .build();
    }

    private String determineNewFileUrl(IssueUpdateDto request, String uploadUrl, Issue oldIssue) {
        String newFileUrl = oldIssue.getFileUrl();

        if (Boolean.TRUE.equals(request.getRemoveImage())) {
            newFileUrl = EMPTY_STRING;
        } else if (!uploadUrl.isBlank()) {
            newFileUrl = uploadUrl;
        }
        return newFileUrl;
    }

    @Transactional
    public IssueResponseDto.ApiMessageDto updateLabels(Long issueId, Set<Long> labelIds) {
        issueRepository.findById(issueId)
                        .orElseThrow(() -> new IssueNotFoundException(issueId));

        //존재하는 레이블인지 확인
        validateLabelIdsExist(labelIds);

        // 기존 매핑 삭제
        labelDao.deleteAllIssueLabelByIssueId(issueId);

        // 새 레이블 매핑 추가
        addNewLabels(issueId, labelIds);

        return createMessageResult(issueId, UPDATE_ISSUE_LABEL);
    }

    private ApiMessageDto createMessageResult(Long id, String message) {
        return ApiMessageDto.builder()
                .id(id)
                .message(message)
                .build();
    }

    private void addNewLabels(Long issueId, Set<Long> labelIds) {
        for (Long labelId : labelIds) {
            IssueLabel issueLabel = IssueLabel.builder()
                    .issueId(issueId)
                    .labelId(labelId)
                    .createdAt(LocalDateTime.now())
                    .build();
            issueLabelRepository.save(issueLabel);
        }
    }

    private void validateLabelIdsExist(Set<Long> labelIds) {
        if (labelIds.isEmpty()) return;

        List<Long> foundIds = labelDao.findExistingLabelIds(labelIds);
        if (foundIds.size() != labelIds.size()) {
            Set<Long> missing = new HashSet<>(labelIds);
            foundIds.forEach(missing::remove);
            throw new LabelNotFoundException(missing);
        }
    }

    public ApiMessageDto updateAssignees(Long issueId, Set<Long> assigneeIds) {
        issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException(issueId));

        //존재하는 담당자인지 확인
        validateAssigneeIdsExist(assigneeIds);

        // 기존 매핑 삭제
        assigneeDao.deleteAllByIssueId(issueId);

        // 새 담당자 매핑 추가
        addNewAssignees(issueId, assigneeIds);

        return createMessageResult(issueId, UPDATE_ISSUE_ASSIGNEE);
    }

    private void addNewAssignees(Long issueId, Set<Long> assigneeIds) {
        for (Long assigneeId : assigneeIds) {
            IssueAssignee issueAssignee = IssueAssignee.builder()
                    .issueId(issueId)
                    .assigneeId(assigneeId)
                    .createdAt(LocalDateTime.now())
                    .build();
            issueAssigneeRepository.save(issueAssignee);
        }
    }


    private void validateAssigneeIdsExist(Set<Long> assigneeIds) {
        if (assigneeIds.isEmpty()) return;

        List<Long> foundIds = userDao.findExistingAssigneeIds(assigneeIds);
        if (foundIds.size() != assigneeIds.size()) {
            Set<Long> missing = new HashSet<>(assigneeIds);
            foundIds.forEach(missing::remove);
            throw new AssigneeNotFoundException(missing);
        }
    }

    @Transactional
    public void deleteIssue(Long issueId, User user) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException(issueId));

        validateAuthor(issue.getAuthorId(), user);

        boolean wasOpen = issue.isOpen();
        issueRepository.deleteById(issueId);
        eventPublisher.publishEvent(new IssueEvent.Deleted(wasOpen));
    }

    private void validateAuthor (Long authorId, User user) {
        if (!authorId.equals(user.getId())) {
            throw new NotAuthorException();
        }
    }
}
