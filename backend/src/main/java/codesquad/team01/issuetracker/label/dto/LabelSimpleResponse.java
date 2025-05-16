package codesquad.team01.issuetracker.label.dto;

import lombok.Builder;

@Builder
public record LabelSimpleResponse(
        Long id,
        String name,
        String color,
        String textColor
) {
}
