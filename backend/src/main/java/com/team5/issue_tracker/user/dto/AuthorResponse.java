package com.team5.issue_tracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {
  private Long id;
  private String username;
  private String imageUrl;
}
