package com.team5.issue_tracker.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.comment.domain.Comment;
import com.team5.issue_tracker.comment.dto.CommentRequest;
import com.team5.issue_tracker.comment.reqository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  @Transactional
  public Long addComment(Long issueId, CommentRequest request) {
    Long userId = 2L;
    Comment comment = new Comment(userId, issueId, request.getContent());
    Comment savedComment = commentRepository.save(comment);

    return savedComment.getId();
  }
}
