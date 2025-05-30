package CodeSquad.IssueTracker.label;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("labels")
@NoArgsConstructor
public class Label {
    @Id
    private Long labelId;
    private String name;
    private String description;
    private String color;
    private LocalDateTime createdAt;

    public Label(String name, String description, String color, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
    }
}
