package com.team5.issue_tracker.issue.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
import com.team5.issue_tracker.issue.repository.IssueRepository;
import com.team5.issue_tracker.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
  private final IssueRepository issueRepository;
  private final UserService userService;

  @Transactional
  public Issue createIssue(IssueCreateRequest request, Long userId) {
    checkIssueCreateRequest(request, userId);

    Issue issue = new Issue(
        request.getTitle(),
        request.getBody(),
        null,
        userId,
        request.getMilestone(),
        true
    );

    return issueRepository.save(issue);
  }

  private void checkIssueCreateRequest(IssueCreateRequest request, Long userId) {
    if (!userService.existsById(userId)) {
      throw new IllegalArgumentException("작성자가 존재하지 않습니다.");
    }

    if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
      throw new IllegalArgumentException("제목을 작성해주세요.");
    }

    if (request.getBody() == null || request.getBody().trim().isEmpty()) {
      throw new IllegalArgumentException("본문을 작성해주세요.");
    }
  }
}
