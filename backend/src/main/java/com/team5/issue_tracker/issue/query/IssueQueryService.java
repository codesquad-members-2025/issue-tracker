package com.team5.issue_tracker.issue.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.issue.dto.response.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.response.IssueSummaryResponse;
import com.team5.issue_tracker.user.dto.UserPageResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IssueQueryService {
  private final IssueQueryRepository issueQueryRepository;

  public IssueQueryService(IssueQueryRepository issueQueryRepository) {
    this.issueQueryRepository = issueQueryRepository;
  }

  public IssuePageResponse getIssuePage() {
    log.debug("전체 이슈 페이지 조회 요청");
    List<IssueSummaryResponse> issueSummaries = issueQueryRepository.findAllIssues();
    return new IssuePageResponse((long) issueSummaries.size(), 0L, (long) issueSummaries.size(),
        issueSummaries);
  }

  public UserPageResponse getIssueAuthors() {
    log.debug("전체 작성자 조회 요청");
    List<UserSummaryResponse> authorSummaries = issueQueryRepository.findDistinctAuthors();
    return new UserPageResponse((long) authorSummaries.size(), 0L, (long) authorSummaries.size(),
        authorSummaries);
  }
}
