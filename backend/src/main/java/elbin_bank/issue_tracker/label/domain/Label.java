package elbin_bank.issue_tracker.label.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("label")
@AllArgsConstructor
@Getter
public class Label extends BaseEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private String color;

    public static Label of(String name, String description, String color) {
        return new Label(null, name, description, color); // id는 DB에서 생성
    }

}
