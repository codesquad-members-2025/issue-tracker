package com.team5.issue_tracker.label.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import lombok.Getter;

@Getter
public class Label {
  @Id
  private Long id;

  private String name;
  private String description;
  private String textColor;
  private String backgroundColor;
  private Instant createdAt;
  private Instant updatedAt;

  public Label(String name, String description, String textColor, String backgroundColor) {
    this.name = name;
    this.description = description;
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PersistenceConstructor
  public Label(Long id, String name, String description, String textColor, String backgroundColor,
      Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
