package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@AllArgsConstructor
@Getter
public class Comment {
    @Id
    private Long id;
    private String content;
    private String imageUrl;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
