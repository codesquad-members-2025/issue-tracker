package com.team5.issue_tracker.user.domain;

import lombok.Getter;

import org.springframework.data.annotation.Id;

@Getter
public class User {

  @Id
  private Long id;

  private final String username;
  private final String email;
  private final String password;
  private final String imageUrl;

  public User(String username, String email, String password, String imageUrl) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.imageUrl = imageUrl;
  }
}
