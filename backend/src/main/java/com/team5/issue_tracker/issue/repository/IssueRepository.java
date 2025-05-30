package com.team5.issue_tracker.issue.repository;

import com.team5.issue_tracker.issue.domain.Issue;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IssueRepository extends CrudRepository<Issue, Long> {

  Optional<Issue> findByTitle(String title);

  void deleteById(Long id);
}
