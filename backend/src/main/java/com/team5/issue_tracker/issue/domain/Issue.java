package com.team5.issue_tracker.issue.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
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
}
