package CodeSquad.IssueTracker.label.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LabelUpdateDto {

    private String name;
    private String description;
    private String color;
    private LocalDateTime createdAt;

    public LabelUpdateDto(String name, String description, String color, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
    }
}
