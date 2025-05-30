package com.team5.issue_tracker.label.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team5.issue_tracker.label.dto.response.LabelPageResponse;
import com.team5.issue_tracker.label.dto.response.LabelResponse;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.issue.dto.response.IssueLabelScrollResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabelQueryService {
  private final LabelQueryRepository labelQueryRepository;

  public IssueLabelScrollResponse getScrolledFilterLabels(String cursor, Integer limit) {
    List<LabelSummaryResponse> labelsPlusOne = labelQueryRepository.findIssueLabels(cursor, limit);

    Boolean hasNext = labelsPlusOne.size() > limit;
    List<LabelSummaryResponse> labels = hasNext ? labelsPlusOne.subList(0, limit) : labelsPlusOne;
    String nextCursor = hasNext ? labels.getLast().getName() : null;

    return new IssueLabelScrollResponse(
        hasNext,
        nextCursor,
        labels
    );
  }

  public LabelPageResponse getPagedLabels(Integer page, Integer perPage) {
    List<LabelResponse> labels = labelQueryRepository.findLabels(page, perPage);
    return new LabelPageResponse(labels.size(), page, perPage, labels);
  }
}
