package CodeSquad.IssueTracker.label.dto;

import lombok.Data;

@Data
public class CreateLabelRequest {
    private String name;
    private String description;
    private String color;
}
