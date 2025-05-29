package CodeSquad.IssueTracker.label.dto;

import CodeSquad.IssueTracker.label.Label;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateLabelRequest {
    @NotBlank(message = "라벨의 이름은 필수로 들어가야합니다")
    private String name;
    private String description;
    private String color;

    public Label toEntity(){
        return new Label(name, description, color, LocalDateTime.now());
    }
}
