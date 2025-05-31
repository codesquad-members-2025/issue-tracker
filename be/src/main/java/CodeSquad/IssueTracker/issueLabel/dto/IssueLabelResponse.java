package CodeSquad.IssueTracker.issueLabel.dto;

import lombok.Data;

@Data
public class IssueLabelResponse {
    private Long labelId;
    private String name;
    private String color;
    private String description;

    public IssueLabelResponse(Long labelId, String name, String color, String description) {
        this.labelId = labelId;
        this.name = name;
        this.color = color;
        this.description = description;
    }
}
