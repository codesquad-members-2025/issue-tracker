package com.team5.issue_tracker.common.comment.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class CommentAttachment {
  @Id
  private Long id;

  private Long commentId;
  private String fileUrl; // s3키 또는 파일경로

  public CommentAttachment(Long commentId, String fileUrl) {
    this.commentId = commentId;
    this.fileUrl = fileUrl;
  }
}
