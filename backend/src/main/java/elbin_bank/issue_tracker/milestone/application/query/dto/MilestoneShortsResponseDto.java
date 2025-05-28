package elbin_bank.issue_tracker.milestone.application.query.dto;

import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneShortProjection;

import java.util.List;

public record MilestoneShortsResponseDto(List<MilestoneShortProjection> milestones) {
}
