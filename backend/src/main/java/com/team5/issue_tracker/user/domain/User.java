package com.team5.issue_tracker.user.domain;

import java.time.Instant;

import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import com.team5.issue_tracker.common.auth.GitHubUser;

@Getter
public class User {

  @Id
  private Long id;

  private final String username;
  private final String email;
  private final String password;
  private final String imageUrl;

  private final Instant createdAt;
  private Instant updatedAt;

  public User(String username, String email, String password, String imageUrl) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.imageUrl = imageUrl;
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PersistenceConstructor
  public User(String username, String email, String password, String imageUrl, Instant createdAt, Instant updatedAt) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.imageUrl = imageUrl;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static User oauthSignup(GitHubUser githubUser) {
    return new User(
        githubUser.getLogin(),
        githubUser.getEmail(),
        "GITHUB_USER",
        githubUser.getAvatar_url()
    );
  }
}
