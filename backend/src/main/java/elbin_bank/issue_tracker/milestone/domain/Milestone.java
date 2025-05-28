package elbin_bank.issue_tracker.milestone.domain;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("milestone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Milestone extends BaseEntity {

    @Id
    private Long id;
    private boolean isClosed;
    private String title;
    private String description;
    private LocalDate expiredAt;

}
