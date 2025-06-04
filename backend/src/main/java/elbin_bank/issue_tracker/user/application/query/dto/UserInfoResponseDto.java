package elbin_bank.issue_tracker.user.application.query.dto;

import elbin_bank.issue_tracker.user.infrastructure.query.projection.UserProjection;

import java.util.List;

public record UserInfoResponseDto(List<UserProjection> users) {
}
