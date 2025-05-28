package elbin_bank.issue_tracker.label.application.query.dto;

import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;

import java.util.List;

public record LabelsResponseDto(List<LabelProjection> labels) {
}
