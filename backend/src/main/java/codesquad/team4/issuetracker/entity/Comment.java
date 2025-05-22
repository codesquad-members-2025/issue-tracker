package codesquad.team4.issuetracker.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table
@AllArgsConstructor
@Getter
@Builder
public class Comment {
    @Id
    @Column("comment_id")
    private Long id;

    @Column("content")
    private String content;

    @Column("file_url")
    private String fileUrl;

    @Column("issue_id")
    private Long issueId;

    @Column("author_id")
    private Long authorId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
