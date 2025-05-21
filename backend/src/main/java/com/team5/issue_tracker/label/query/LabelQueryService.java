package com.team5.issue_tracker.label.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.label.dto.response.LabelPageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelQueryService {
  private final LabelQueryRepository labelQueryRepository;

  public LabelPageResponse getAllLables() {
    List<LabelSummaryResponse> labels = labelQueryRepository.findAllLables();
    return new LabelPageResponse((long) labels.size(), 0L, (long) labels.size(), labels);
  }
}
