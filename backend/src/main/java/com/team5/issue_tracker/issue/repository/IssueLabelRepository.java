package com.team5.issue_tracker.issue.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.team5.issue_tracker.issue.domain.IssueLabel;

public interface IssueLabelRepository extends CrudRepository<IssueLabel, Long> {
  List<IssueLabel> findAllByIssueId(Long issueId);
}
