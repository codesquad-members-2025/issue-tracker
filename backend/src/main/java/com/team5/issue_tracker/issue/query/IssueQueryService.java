package com.team5.issue_tracker.issue.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.issue.dto.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.IssueSummaryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IssueQueryService {
  IssueQueryRepository issueQueryRepository;

  public IssueQueryService(IssueQueryRepository issueQueryRepository) {
    this.issueQueryRepository = issueQueryRepository;
  }

  public IssuePageResponse getIssuePage() {
    log.debug("전체 이슈 페이지 조회 요청");
    List<IssueSummaryResponse> issueSummaries = issueQueryRepository.findAllIssues();
    return new IssuePageResponse((long) issueSummaries.size(), 0L, (long) issueSummaries.size(),
        issueSummaries);
  }
}
