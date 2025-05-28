package CodeSquad.IssueTracker.label.dto;

import CodeSquad.IssueTracker.label.Label;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateLabelRequest {
    private String name;
    private String description;
    private String color;

    public Label toEntity(){
        return new Label(name, description, color, LocalDateTime.now());
    }
}
