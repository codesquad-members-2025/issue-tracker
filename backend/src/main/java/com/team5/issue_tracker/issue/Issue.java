package com.team5.issue_tracker.issue;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Issue {
    @Id
    private Long id;

    private final String title;
    private final String body;
    private final String imageUrl;
    private final Long userId;
    private final Long milestoneId;
    private final boolean isOpen;
    private final LocalDateTime createdAt;
}
