package com.team5.issue_tracker.label.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.label.dto.response.LabelPageResponse;
import com.team5.issue_tracker.label.dto.response.LabelResponse;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.label.dto.response.IssueLabelPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelQueryService {
  private final LabelQueryRepository labelQueryRepository;

  public IssueLabelPageResponse getFilterLabels() {
    List<LabelSummaryResponse> labels = labelQueryRepository.findIssueLabels();
    return new IssueLabelPageResponse((long) labels.size(), 0L, (long) labels.size(), labels);
  }

  public LabelPageResponse getAllLabels() {
    List<LabelResponse> labels = labelQueryRepository.findAllLabels();
    return new LabelPageResponse((long) labels.size(), 0L, (long) labels.size(), labels);
  }
}
