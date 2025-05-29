package com.team5.issue_tracker.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.issue_tracker.comment.dto.UpdateCommentRequest;
import com.team5.issue_tracker.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentService commentService;

  @PatchMapping("/{commentId}")
  public ResponseEntity<Void> patchComment(@PathVariable Long commentId,
      @RequestBody UpdateCommentRequest updateCommentRequest) {
    commentService.editComment(commentId, updateCommentRequest);

    return ResponseEntity.noContent().build();
  }
}
