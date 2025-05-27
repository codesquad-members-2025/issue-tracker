package com.team5.issue_tracker.issue.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.common.comment.domain.Comment;
import com.team5.issue_tracker.common.comment.domain.CommentAttachment;
import com.team5.issue_tracker.common.comment.dto.CommentRequest;
import com.team5.issue_tracker.common.comment.reqository.CommentAttachmentRepository;
import com.team5.issue_tracker.common.comment.reqository.CommentRepository;
import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.domain.IssueAssignee;
import com.team5.issue_tracker.issue.domain.IssueLabel;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
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
  private final CommentAttachmentRepository commentAttachmentRepository;
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

    CommentRequest commentRequest = request.getComment();
    Comment comment = new Comment(userId, savedIssueID, commentRequest.getContent(), now, now);
    Comment savedComment = commentRepository.save(comment);

    saveCommentAttachment(commentRequest, savedComment);

    return savedIssueID;
  }

  private void saveIssueLabels(Long issueId, List<Long> labelIds) {
    for (Long labelId : labelIds) {
      issueLabelRepository.save(new IssueLabel(issueId, labelId));
    }
  }

  private void saveIssueAssignees(Long issueId, List<Long> assigneeIds) {
    for (Long assigneeId : assigneeIds) {
      issueAssigneeRepository.save(new IssueAssignee(issueId, assigneeId));
    }
  }

  private void saveCommentAttachment(CommentRequest commentRequest, Comment savedComment) {
    List<String> attachments = commentRequest.getAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      for (String fileUrl : attachments) {
        CommentAttachment attachment = new CommentAttachment(
            savedComment.getId(),
            fileUrl
        );
        commentAttachmentRepository.save(attachment);
      }
    }
  }
}
