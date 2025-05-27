package com.team5.issue_tracker.common.comment.dto;

import java.time.Instant;

import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
  private Long id;
  private String content;
  private UserSummaryResponse author;
  private Instant createdAt;
  private Instant updatedAt;
}
