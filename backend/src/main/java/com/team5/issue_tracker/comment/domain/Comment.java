package com.team5.issue_tracker.comment.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
  @Id
  private Long id;

  private Long userId;
  private Long issueId;
  private String content;
  private Instant createdAt;
  private Instant updatedAt;

  public Comment(Long userId, Long issueId, String content) {
    this.userId = userId;
    this.issueId = issueId;
    this.content = content;
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  public Comment(Long userId, Long issueId, String content, Instant createdAt, Instant updatedAt) {
    this.userId = userId;
    this.issueId = issueId;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @PersistenceCreator
  public Comment(Long id, Long userId, Long issueId, String content, Instant createdAt,
      Instant updatedAt) {
    this.id = id;
    this.userId = userId;
    this.issueId = issueId;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
