package com.team5.issue_tracker.comment.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.comment.domain.Comment;
import com.team5.issue_tracker.comment.dto.CommentRequest;
import com.team5.issue_tracker.comment.reqository.CommentRepository;
import com.team5.issue_tracker.common.exception.ErrorCode;
import com.team5.issue_tracker.common.exception.NotFoundException;
import com.team5.issue_tracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long addComment(Long issueId, CommentRequest request, Long userId) {
    validateUserExists(userId);
    Comment comment = new Comment(userId, issueId, request.getContent());
    Comment savedComment = commentRepository.save(comment);

    return savedComment.getId();
  }

  @Transactional
  public void editComment(Long commentId, CommentRequest updateCommentRequest, Long userId) {
    validateUserExists(userId);

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

    if(!comment.getUserId().equals(userId)){
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    comment.setContent(updateCommentRequest.getContent());
    comment.setUpdatedAt(Instant.now());
    commentRepository.save(comment);
  }

  private void validateUserExists(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }
  }
}
