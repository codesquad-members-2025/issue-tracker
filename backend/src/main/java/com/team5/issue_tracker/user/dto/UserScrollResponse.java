package com.team5.issue_tracker.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserScrollResponse {
  private Boolean hasNext;
  private String nextCursor;
  private List<UserSummaryResponse> users;
}
