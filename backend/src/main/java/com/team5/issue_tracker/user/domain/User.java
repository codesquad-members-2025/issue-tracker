package com.team5.issue_tracker.user.domain;

import java.time.Instant;

import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter
public class User {

  @Id
  private Long id;

  private final String username;
  private final String email;
  private final String password;
  private final String imageUrl;
  private Instant createdAt;
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
}
