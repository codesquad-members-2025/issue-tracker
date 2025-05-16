package CodeSquad.IssueTracker.label.dto;

import lombok.Data;

@Data
public class LabelResponse {
    private Long id;
    private String name;
    private String color;
    private String description;
}

