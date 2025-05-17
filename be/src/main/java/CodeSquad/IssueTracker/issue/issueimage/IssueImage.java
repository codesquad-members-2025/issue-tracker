package CodeSquad.IssueTracker.issue.issueimage;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("issue_images")
public class IssueImage {
    @Id
    private Long id;

    private Long issueId;
    private String imageUrl;
    private String originalName;
    private String contentType;
}

