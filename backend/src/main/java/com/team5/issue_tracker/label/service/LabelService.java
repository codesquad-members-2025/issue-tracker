package com.team5.issue_tracker.label.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.label.dto.LabelResponse;

@Service
public class LabelService {
  public List<LabelResponse> getLabelResponsesByIssueId(Long id) {
    return List.of(new LabelResponse(id, "labelName", "labelImageUrl"));
  }
}
