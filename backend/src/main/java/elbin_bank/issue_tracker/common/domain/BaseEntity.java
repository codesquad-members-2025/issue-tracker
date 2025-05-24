package elbin_bank.issue_tracker.common.domain;

import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    public void markDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

}
