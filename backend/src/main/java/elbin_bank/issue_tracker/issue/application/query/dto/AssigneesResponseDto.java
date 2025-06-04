package elbin_bank.issue_tracker.issue.application.query.dto;

import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;

import java.util.List;

public record AssigneesResponseDto(List<UserInfoProjection> assignees) {
}
