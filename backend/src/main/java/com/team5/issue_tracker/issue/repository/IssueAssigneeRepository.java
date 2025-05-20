package com.team5.issue_tracker.issue.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.team5.issue_tracker.issue.domain.IssueAssignee;

public interface IssueAssigneeRepository extends CrudRepository<IssueAssignee, Long> {
  List<IssueAssignee> findByIssueId(Long issueId);
}
