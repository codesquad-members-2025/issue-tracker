package com.team5.issue_tracker.issue.service;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.issue.repository.IssueRepository;
import com.team5.issue_tracker.label.service.LabelService;
import com.team5.issue_tracker.milestone.service.MilestoneService;
import com.team5.issue_tracker.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
  private final IssueRepository issueRepository;
  private final UserService userService;
  private final LabelService labelService;
  private final MilestoneService milestoneService;




}
