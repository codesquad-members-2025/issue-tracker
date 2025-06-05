package com.team5.issue_tracker.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUser {
  private Long id;
  private String login;
  private String avatar_url;
  private String email;
}
