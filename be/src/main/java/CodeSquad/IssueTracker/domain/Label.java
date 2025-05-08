package CodeSquad.IssueTracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("labels")
public class Label {
    @Id
    private Long id;
    private String name;
    private String color;
}

