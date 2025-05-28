package com.team5.issue_tracker.milestone.repository;

import org.springframework.data.repository.CrudRepository;
import com.team5.issue_tracker.milestone.domain.Milestone;

public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
}
