package com.team5.issue_tracker.user.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.user.dto.UserPageResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserQueryService {
  private final UserQueryRepository userQueryRepository;

  public UserQueryService(UserQueryRepository userQueryRepository) {
    this.userQueryRepository = userQueryRepository;
  }

  public UserPageResponse getAllUsers() {
    log.debug("전체 유저 조회 요청");
    List<UserSummaryResponse> users = userQueryRepository.findAllUsers();
    return new UserPageResponse((long) users.size(), 0L, (long) users.size(), users);
  }
}
