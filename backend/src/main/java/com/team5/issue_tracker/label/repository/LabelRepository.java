package com.team5.issue_tracker.label.repository;

import org.springframework.data.repository.CrudRepository;
import com.team5.issue_tracker.label.domain.Label;

public interface LabelRepository extends CrudRepository<Label, Long> {
}
