package com.team5.issue_tracker.user.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.user.dto.UserScrollResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryService {
  private final UserQueryRepository userQueryRepository;

  public UserScrollResponse getScrolledUsers(String cursor, Integer limit) {
    log.debug("유저 스크롤 조회 요청");
    List<UserSummaryResponse> usersPlusOne = userQueryRepository.getScrolledUsers(cursor, limit);

    Boolean hasNext = usersPlusOne.size() > limit;
    List<UserSummaryResponse> users = hasNext ? usersPlusOne.subList(0, limit) : usersPlusOne;
    String nextCursor = hasNext ? users.getLast().getUsername() : null;

    return new UserScrollResponse(hasNext, nextCursor, users);
  }
}
