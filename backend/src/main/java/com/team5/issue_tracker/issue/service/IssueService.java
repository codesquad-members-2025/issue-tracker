package com.team5.issue_tracker.issue.service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.comment.domain.Comment;
import com.team5.issue_tracker.comment.dto.CommentRequest;
import com.team5.issue_tracker.comment.reqository.CommentRepository;
import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.NotFoundException;
import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.domain.IssueAssignee;
import com.team5.issue_tracker.issue.domain.IssueLabel;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
import com.team5.issue_tracker.issue.dto.request.IssueDeleteRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateBulkIssueStatusRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueAssigneesRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueLabelsRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueMilestoneRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueStatusRequest;
import com.team5.issue_tracker.issue.dto.request.UpdateIssueTitleRequest;
import com.team5.issue_tracker.issue.query.IssueQueryRepository;
import com.team5.issue_tracker.issue.repository.IssueAssigneeRepository;
import com.team5.issue_tracker.issue.repository.IssueLabelRepository;
import com.team5.issue_tracker.issue.repository.IssueRepository;
import com.team5.issue_tracker.user.domain.User;
import com.team5.issue_tracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
  private final IssueRepository issueRepository;
  private final IssueLabelRepository issueLabelRepository;
  private final IssueAssigneeRepository issueAssigneeRepository;
  private final CommentRepository commentRepository;
  private final IssueQueryRepository issueQueryRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long createIssue(IssueCreateRequest request, Long userId) {
    validateUserExists(userId);

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
  public void updateIssueTitle(Long issueId, UpdateIssueTitleRequest request, Long userId) {
    String title = request.getTitle();
    Issue issue = getIssueOrThrow(issueId);

    validateUserOwnsIssue(userId, issueId);

    issue.setTitle(title);
    issue.setUpdatedAt(Instant.now());
    issueRepository.save(issue);
  }

  @Transactional
  public void updateIssueStatus(Long issueId, UpdateIssueStatusRequest request, Long userId) {
    Boolean isOpen = request.getIsOpen();
    Issue issue = getIssueOrThrow(issueId);

    validateUserOwnsIssue(userId, issueId);

    issue.setIsOpen(isOpen);
    issueRepository.save(issue);
  }

  @Transactional
  public void updateIssueLabels(Long issueId, UpdateIssueLabelsRequest request, Long userId) {
    Set<Long> labelIds = request.getLabelIds();

    Issue issue = getIssueOrThrow(issueId);

    validateUserOwnsIssue(userId, issueId);

    // 스프링 데이터 jdbc에서는 한 번에 지우는게 안되는 것 같아서 분리
    List<IssueLabel> issueLabels = issueLabelRepository.findAllByIssueId(issueId);
    issueLabelRepository.deleteAll(issueLabels);

    if (labelIds != null && !labelIds.isEmpty()) {
      saveIssueLabels(issueId, labelIds);
    }
    issue.setUpdatedAt(Instant.now());
  }

  @Transactional
  public void updateIssueMilestone(Long issueId, UpdateIssueMilestoneRequest request, Long userId) {
    Long milestoneId = request.getMilestoneId();
    Issue issue = getIssueOrThrow(issueId);

    validateUserOwnsIssue(userId, issueId);

    issue.setMilestoneId(milestoneId);
    issue.setUpdatedAt(Instant.now());
    issueRepository.save(issue);
  }

  @Transactional
  public void updateIssueAssignees(Long issueId, UpdateIssueAssigneesRequest request, Long userId) {
    Set<Long> assigneeIds = request.getAssigneeIds();

    Issue issue = getIssueOrThrow(issueId);

    validateUserOwnsIssue(userId, issueId);

    // 스프링 데이터 jdbc에서는 한 번에 지우는게 안되는 것 같아서 분리
    List<IssueAssignee> issueAssignees = issueAssigneeRepository.findAllByIssueId(issueId);
    issueAssigneeRepository.deleteAll(issueAssignees);

    if (assigneeIds != null && !assigneeIds.isEmpty()) {
      saveIssueAssignees(issueId, assigneeIds);
    }
    issue.setUpdatedAt(Instant.now());
  }

  @Transactional
  public void deleteIssue(Long issueId, Long userId) {
    validateUserOwnsIssue(userId, issueId);
    issueRepository.deleteById(issueId);
  }

  @Transactional
  public void deleteIssues(IssueDeleteRequest request, Long userId) {
    validateUserExists(userId);
    Iterable<Issue> issues = issueRepository.findAllById(request.getIssueIds());
    for(Issue issue : issues) {
      validateUserOwnsIssue(userId, issue.getId());
    }
    issueRepository.deleteAll(issues);
  }

  @Transactional
  public void updateBulkIssuesStatus(UpdateBulkIssueStatusRequest request, Long userId) {
    List<Long> issueIds = request.getIssueIds();
    Boolean isOpen = request.getIsOpen();

    List<Issue> issues = issueQueryRepository.findAllByIds(issueIds);

    for (Long issueId : issueIds) {
      validateUserOwnsIssue(userId, issueId);
    }

    for (Issue issue : issues) {
      issue.setIsOpen(isOpen);
      issue.setUpdatedAt(Instant.now());
    }
    issueRepository.saveAll(issues);
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

  private void validateIssueExists(Long issueId) {
    if (!issueRepository.existsById(issueId)) {
      throw new NotFoundException(ErrorCode.ISSUE_NOT_FOUND);
    }
  }

  private Issue getIssueOrThrow(Long issueId) {
    return issueRepository.findById(issueId)
        .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));
  }

  private void validateUserExists(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }
  }

  private void validateUserOwnsIssue(Long userId, Long issueId) {
    validateIssueExists(issueId);
    validateUserExists(userId);
    Issue issue = issueRepository.findById(issueId).get();

    if (!issue.getUserId().equals(userId)) {
      throw new NotFoundException(ErrorCode.ISSUE_NOT_FOUND);
    }
  }
}
