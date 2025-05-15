package codesquad.team01.issuetracker.label.dto;

public record LabelSimpleResponse(
        Long id,
        String name,
        String color,
        String textColor
) {
}
