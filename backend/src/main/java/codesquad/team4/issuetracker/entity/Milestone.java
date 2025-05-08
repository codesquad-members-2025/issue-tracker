package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("milestone")
@AllArgsConstructor
@Getter
public class Milestone {
    @Id
    private Long id;
    private String name;
    private String description;
    private LocalDateTime endDate;
    private boolean isOpen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
