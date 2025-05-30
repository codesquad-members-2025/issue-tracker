package CodeSquad.IssueTracker.label.dto;

import lombok.Data;

@Data
public class LabelResponse {
    private Long id;
    private String name;
    private String color;
    private String description;

    public LabelResponse(Long labelId, String name, String description, String color) {
        this.id = labelId;
        this.name = name;
        this.description = description;
        this.color = color;
    }
}

