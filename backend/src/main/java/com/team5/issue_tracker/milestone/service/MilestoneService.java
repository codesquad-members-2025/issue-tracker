package com.team5.issue_tracker.milestone.service;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.milestone.dto.MilestoneResponse;

@Service
public class MilestoneService {
  public MilestoneResponse getMilestoneResponseById(Long id) {
    return new MilestoneResponse(id, "milestoneTitle");
  }
}
