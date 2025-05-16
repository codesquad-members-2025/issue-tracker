package com.team5.issue_tracker.issue.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Issue {
    @Id
    private Long id;

    private final String title;
    private final String body;
    private final String imageUrl;
    private final Long userId;
    private final Long milestoneId;
    private final boolean isOpen;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

  public Issue(String title, String body, String imageUrl,
      Long userId, Long milestoneId, boolean isOpen) {
    this.title = title;
    this.body = body;
    this.imageUrl = imageUrl;
    this.userId = userId;
    this.milestoneId = milestoneId;
    this.isOpen = isOpen;
  }
}
