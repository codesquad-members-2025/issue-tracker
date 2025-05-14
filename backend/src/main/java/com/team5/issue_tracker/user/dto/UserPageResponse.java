package com.team5.issue_tracker.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageResponse {
  private Long total;
  private Long page;
  private Long perPage;
  private List<UserSummaryResponse> users;
}
