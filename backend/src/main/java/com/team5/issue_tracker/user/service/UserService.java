package com.team5.issue_tracker.user.service;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

@Service
public class UserService {
  public UserSummaryResponse getAuthorResponseById(Long id) {
    return new UserSummaryResponse(id, "authorName", "authorImageUrl");
  }
}
