package com.team5.issue_tracker.label.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;

@Service
public class LabelService {
  public List<LabelSummaryResponse> getLabelResponsesByIssueId(Long id) {
    return List.of(new LabelSummaryResponse(id, "labelName", "labelTextColor", "labelBackgroundColor"));
  }
}
