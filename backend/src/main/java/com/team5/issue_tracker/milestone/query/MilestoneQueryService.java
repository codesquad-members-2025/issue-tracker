package com.team5.issue_tracker.milestone.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team5.issue_tracker.issue.dto.response.IssueMilestoneScrollResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneCountResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestonePageResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneQueryService {
  private final MilestoneQueryRepository milestoneQueryRepository;

  public IssueMilestoneScrollResponse getScrolledFilterMilestones(String cursor, Integer limit) {
    List<MilestoneSummaryResponse> milestonesPlusOne = milestoneQueryRepository.findIssueMilestones(cursor, limit);

    Boolean hasNext = milestonesPlusOne.size() > limit;
    List<MilestoneSummaryResponse> milestones = hasNext ? milestonesPlusOne.subList(0, limit) : milestonesPlusOne;
    String nextCursor = hasNext ? milestones.getLast().getName() : null;

    return new IssueMilestoneScrollResponse(
        hasNext,
        nextCursor,
        milestones
    );
  }

  public MilestonePageResponse getPagedMilestones(Integer page, Integer perPage) {
    List<MilestoneResponse> milestones = milestoneQueryRepository.findMilestones(page, perPage);
    MilestoneCountResponse count = milestoneQueryRepository.countMilestones();

    return new MilestonePageResponse(
        milestones.size(),
        page,
        perPage,
        count.getOpenCount(),
        count.getClosedCount(),
        milestones
    );
  }
}
