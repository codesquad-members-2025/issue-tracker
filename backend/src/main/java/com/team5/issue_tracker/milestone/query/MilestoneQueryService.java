package com.team5.issue_tracker.milestone.query;

import java.util.List;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.milestone.dto.response.IssueMilestonePageResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneCountResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestonePageResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MilestoneQueryService {
  private final MilestoneQueryRepository milestoneQueryRepository;

  public IssueMilestonePageResponse getFilterMilestones() {
    List<MilestoneSummaryResponse> milestones = milestoneQueryRepository.findIssueMilestones();
    return new IssueMilestonePageResponse((long) milestones.size(), 0L, (long) milestones.size(),
        milestones);
  }

  public MilestonePageResponse getAllMilestones() {
    List<MilestoneResponse> milestones = milestoneQueryRepository.findAllMilestones();
    MilestoneCountResponse count = milestoneQueryRepository.countMilestones();

    return new MilestonePageResponse(
        count.getTotal(),
        1L,
        (long) milestones.size(),
        count.getOpenCount(),
        count.getClosedCount(),
        milestones
    );
  }
}
