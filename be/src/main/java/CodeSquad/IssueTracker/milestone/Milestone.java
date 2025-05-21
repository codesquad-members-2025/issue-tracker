package CodeSquad.IssueTracker.milestone;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("milestones")
public class Milestone {
    @Id
    private Long milestoneId;
    private String name;
    private String description;
    private LocalDateTime endDate;
    private Boolean isOpen;
    private LocalDateTime lastModifiedAt;
    private Long processingRate;
}
