package com.team5.issue_tracker.common.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.common.comment.domain.Comment;
import com.team5.issue_tracker.common.comment.domain.CommentAttachment;
import com.team5.issue_tracker.common.comment.dto.CommentRequest;
import com.team5.issue_tracker.common.comment.reqository.CommentAttachmentRepository;
import com.team5.issue_tracker.common.comment.reqository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final CommentAttachmentRepository commentAttachmentRepository;

  @Transactional
  public Long addComment(Long issueId, CommentRequest request) {
    Long userId = 2L;
    Comment comment = new Comment(userId, issueId, request.getContent());
    Comment savedComment = commentRepository.save(comment);
    saveCommentAttachment(request, savedComment);

    return savedComment.getId();
  }

  private void saveCommentAttachment(CommentRequest request, Comment savedComment) {
    List<String> attachments = request.getAttachments();
    if (attachments != null && !attachments.isEmpty()) {
      for (String fileUrl : attachments) {
        CommentAttachment attachment = new CommentAttachment(savedComment.getId(), fileUrl);
        commentAttachmentRepository.save(attachment);
      }
    }
  }
}
