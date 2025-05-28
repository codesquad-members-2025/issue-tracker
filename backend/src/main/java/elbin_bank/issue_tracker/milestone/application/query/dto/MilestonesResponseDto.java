package elbin_bank.issue_tracker.milestone.application.query.dto;

import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;

import java.util.List;

public record MilestonesResponseDto(List<MilestoneProjection> milestones) {
}
