package com.team5.issue_tracker.issue.service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.common.comment.domain.Comment;
import com.team5.issue_tracker.common.comment.dto.CommentRequest;
import com.team5.issue_tracker.common.comment.reqository.CommentRepository;
import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.domain.IssueAssignee;
import com.team5.issue_tracker.issue.domain.IssueLabel;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
import com.team5.issue_tracker.issue.dto.request.IssueDeleteRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueAssigneesRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueLabelsRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueMilestoneRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueStatusRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueTitleRequest;
import com.team5.issue_tracker.issue.repository.IssueAssigneeRepository;
import com.team5.issue_tracker.issue.repository.IssueLabelRepository;
import com.team5.issue_tracker.issue.repository.IssueRepository;
import com.team5.issue_tracker.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
  private final IssueRepository issueRepository;
  private final IssueLabelRepository issueLabelRepository;
  private final IssueAssigneeRepository issueAssigneeRepository;
  private final CommentRepository commentRepository;
  private final UserService userService;

  @Transactional
  public Long createIssue(IssueCreateRequest request) {
    Long userId = 1L; // TODO: 유저가 없으니 우선 임시데이터 넣음!, 유저가 없으면 생성 불가!
    if (!userService.existsById(userId)) {
      throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    } //TODO: 커스텀 에러 만들지 고민중

    Instant now = Instant.now();
    Issue issue = new Issue(request.getTitle(), userId, request.getMilestoneId(), true, now, now);
    Issue savedIssue = issueRepository.save(issue);
    Long savedIssueID = savedIssue.getId();

    saveIssueLabels(savedIssueID, request.getLabelIds());
    saveIssueAssignees(savedIssueID, request.getAssigneeIds());

    CommentRequest content = request.getComment();
    Comment comment = new Comment(userId, savedIssueID, content.getContent(), now, now);
    commentRepository.save(comment);

    return savedIssueID;
  }

  @Transactional
  public void updateIssueTitle(Long issueId, UpdateIssueTitleRequest request) {
    String title = request.getTitle();
    Issue issue = getIssueOrThrow(issueId);

    issue.setTitle(title);
    issueRepository.save(issue);
  }

  @Transactional
  public void updateIssueStatus(Long issueId, UpdateIssueStatusRequest request) {
    Boolean isOpen = request.getIsOpen();
    Issue issue = getIssueOrThrow(issueId);

    issue.setIsOpen(isOpen);
    issueRepository.save(issue);
  }

  @Transactional
  public void updateIssueLabels(Long issueId, UpdateIssueLabelsRequest request) {
    Set<Long> labelIds = request.getLabelIds();

    validateIssueExists(issueId);
    // 스프링 데이터 jdbc에서는 한 번에 지우는게 안되는 것 같아서 분리
    List<IssueLabel> issueLabels = issueLabelRepository.findAllByIssueId(issueId);
    issueLabelRepository.deleteAll(issueLabels);

    if (labelIds != null && !labelIds.isEmpty()) {
      saveIssueLabels(issueId, labelIds);
    }
  }

  @Transactional
  public void updateIssueMilestone(Long issueId, UpdateIssueMilestoneRequest request) {
    Long milestoneId = request.getMilestoneId();
    Issue issue = getIssueOrThrow(issueId);

    issue.setMilestoneId(milestoneId);
    issueRepository.save(issue);
  }

  @Transactional
  public void updateIssueAssignees(Long issueId, UpdateIssueAssigneesRequest request) {
    Set<Long> assigneeIds = request.getAssigneeIds();

    validateIssueExists(issueId);
    // 스프링 데이터 jdbc에서는 한 번에 지우는게 안되는 것 같아서 분리
    List<IssueAssignee> issueAssignees = issueAssigneeRepository.findAllByIssueId(issueId);
    issueAssigneeRepository.deleteAll(issueAssignees);

    if (assigneeIds != null && !assigneeIds.isEmpty()) {
      saveIssueAssignees(issueId, assigneeIds);
    }
  }

  @Transactional
  public void deleteIssue(Long issueId) {
    validateIssueExists(issueId);
    issueRepository.deleteById(issueId);
  }

  @Transactional
  public void deleteIssues(IssueDeleteRequest request) {
    Iterable<Issue> issues = issueRepository.findAllById(request.getIssueIds());
    issueRepository.deleteAll(issues);
  }

  private void saveIssueLabels(Long issueId, Collection<Long> labelIds) {
    List<IssueLabel> issueLabels = labelIds.stream()
        .map(labelId -> new IssueLabel(issueId, labelId))
        .toList();

    issueLabelRepository.saveAll(issueLabels);
  }

  private void saveIssueAssignees(Long issueId, Collection<Long> assigneeIds) {
    List<IssueAssignee> issueAssignees = assigneeIds.stream()
        .map(assigneeId -> new IssueAssignee(issueId, assigneeId))
        .toList();

    issueAssigneeRepository.saveAll(issueAssignees);
  }

  private void saveIssueAssignees(Long issueId, List<Long> assigneeIds) {
    for (Long assigneeId : assigneeIds) {
      issueAssigneeRepository.save(new IssueAssignee(issueId, assigneeId));
    }
  }

  private void validateIssueExists(Long issueId) {
    if (!issueRepository.existsById(issueId)) {
      throw new IllegalArgumentException("존재하지 않는 이슈입니다.");
    } //TODO: 커스텀 에러 만들지 고민중
  }

  private Issue getIssueOrThrow(Long issueId) {
    return issueRepository.findById(issueId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이슈입니다."));
  } //TODO: 커스텀 에러 만들지 고민중
}
