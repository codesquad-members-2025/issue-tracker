package com.team5.issue_tracker.user.service;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.user.dto.AuthorResponse;

@Service
public class UserService {
  public AuthorResponse getAuthorResponseById(Long id) {
    return new AuthorResponse(id, "authorName","authorImageUrl");
  }
}
