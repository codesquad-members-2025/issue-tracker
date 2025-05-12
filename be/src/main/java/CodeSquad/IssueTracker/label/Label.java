package CodeSquad.IssueTracker.label;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("labels")
public class Label {
    @Id
    private Long labelId;
    private String name;
    private String description;
    private String color;
    private LocalDateTime createdAt;
}
