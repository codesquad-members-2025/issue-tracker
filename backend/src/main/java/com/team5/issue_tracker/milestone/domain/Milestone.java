package com.team5.issue_tracker.milestone.domain;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import lombok.Getter;

@Getter
public class Milestone {
  @Id
  private Long id;

  private String name;
  private LocalDate deadline;
  private String description;
  private Boolean isOpen;
  private Instant createdAt;
  private Instant updatedAt;

  public Milestone(String name, LocalDate deadline, String description, Boolean isOpen) {
    this.name = name;
    this.deadline = deadline;
    this.description = description;
    this.isOpen = isOpen;
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PersistenceConstructor
  public Milestone(Long id, String name, LocalDate deadline, String description, Boolean isOpen, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.deadline = deadline;
    this.description = description;
    this.isOpen = isOpen;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
