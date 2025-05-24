package elbin_bank.issue_tracker.label.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("label")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Label extends BaseEntity {

    @Id
    private long id;
    private String name;
    private String description;
    private String color;

}
