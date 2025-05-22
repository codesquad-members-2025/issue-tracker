package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("label")
@AllArgsConstructor
@Getter
@Builder
public class Label {
    @Id
    @Column("label_id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("color")
    private String color;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
