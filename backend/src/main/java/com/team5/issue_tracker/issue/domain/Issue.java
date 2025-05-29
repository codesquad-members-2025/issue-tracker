package com.team5.issue_tracker.issue.domain;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter
@Setter
@AllArgsConstructor
public class Issue {
  @Id
  private Long id;

  private String title;
  private Long milestoneId;
  private Boolean isOpen;

  private final Long userId;
  private final Instant createdAt;
  private Instant updatedAt;

  public Issue(String title, Long userId, Long milestoneId, Boolean isOpen) {
    this.title = title;
    this.userId = userId;
    this.milestoneId = milestoneId;
    this.isOpen = isOpen;
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  public Issue(String title, Long userId, Long milestoneId, Boolean isOpen, Instant createdAt,
      Instant updatedAt) {
    this.title = title;
    this.userId = userId;
    this.milestoneId = milestoneId;
    this.isOpen = isOpen;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @PersistenceConstructor
  public Issue(Long id, String title, Long userId, Long milestoneId, Boolean isOpen,
      Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.title = title;
    this.userId = userId;
    this.milestoneId = milestoneId;
    this.isOpen = isOpen;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
