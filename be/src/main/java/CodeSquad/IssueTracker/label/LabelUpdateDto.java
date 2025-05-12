package CodeSquad.IssueTracker.label;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LabelUpdateDto {

    private String name;
    private String description;
    private String color;
    private LocalDateTime createdAt;
}
