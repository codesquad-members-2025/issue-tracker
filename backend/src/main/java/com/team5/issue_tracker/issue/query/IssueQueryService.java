package com.team5.issue_tracker.issue.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.issue.dto.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.IssueSummaryResponse;

@Service
public class IssueQueryService {
  IssueQueryRepository issueQueryRepository;

  public IssueQueryService(IssueQueryRepository issueQueryRepository) {
    this.issueQueryRepository = issueQueryRepository;
  }

  public IssuePageResponse getIssueList() {
    List<IssueSummaryResponse> issueSummaries = issueQueryRepository.findAllIssues();
    return new IssuePageResponse((long) issueSummaries.size(), 0L, (long) issueSummaries.size(),
        issueSummaries);
  }
}
